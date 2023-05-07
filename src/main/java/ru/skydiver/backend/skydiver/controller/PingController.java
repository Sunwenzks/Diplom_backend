package ru.skydiver.backend.skydiver.controller;

import org.openapitools.api.InfoApi;
import org.openapitools.model.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PingController implements InfoApi {

    public PingController() {
    }

    @Override
    public ResponseEntity<Status> info() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(new Status().result("Все ок " + userName));
    }
}
