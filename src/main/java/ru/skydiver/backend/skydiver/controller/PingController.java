package ru.skydiver.backend.skydiver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/info")
    public String getInfo() {
        return "Ксюша очень красивая";
    }

    //браузер -> http ->приложение сприга -> вызывается метод контроллера -> вызывается сервис добавление пользователя -> dao
}
