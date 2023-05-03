package ru.skydiver.backend.skydiver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    public PingController() {
    }

    @GetMapping("/info")
    public String getInfo(Authentication authentication) {
        return "Страничка работает " + authentication.getName();
    }
}
