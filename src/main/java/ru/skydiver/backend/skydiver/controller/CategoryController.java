package ru.skydiver.backend.skydiver.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skydiver.backend.skydiver.model.CategoryDto;
import ru.skydiver.backend.skydiver.services.CategoryService;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/all")
    public List<CategoryDto> getCategoryAll() {
       return categoryService.getAllCategories();
    }
    @GetMapping("/category/main")
    public List<CategoryDto> getMainCategory() {
        return categoryService.getMainCategory();
    }
}
