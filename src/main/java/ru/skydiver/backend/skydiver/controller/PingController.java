package ru.skydiver.backend.skydiver.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import ru.skydiver.backend.skydiver.repositories.CategoryRepository;
import ru.skydiver.backend.skydiver.services.CategoryService;

@RestController
public class PingController {

    private final CategoryService categoryService;
    public PingController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/info")
    public String getInfo() {
        var name = "Страничка работает";

        return "ok";
    }

    //браузер -> http ->приложение сприга -> вызывается метод контроллера -> вызывается сервис добавление пользователя -> dao
    //
}
