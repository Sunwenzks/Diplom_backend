package ru.skydiver.backend.skydiver.controller;

import org.openapitools.api.InfoApi;
import org.openapitools.model.InfoGet200Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PingController implements InfoApi {

    public PingController() {
    }

    @Override
    public ResponseEntity<InfoGet200Response> infoGet() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(new InfoGet200Response().result("Все ок " + userName));
    }
}
