package ru.skydiver.backend.skydiver.controller;


import java.util.stream.Collectors;

import org.openapitools.api.CategoryApi;
import org.openapitools.model.Category;
import org.openapitools.model.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skydiver.backend.skydiver.model.CategoryEntity;
import ru.skydiver.backend.skydiver.services.CategoryService;

@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<CategoryResponse> categoryList(Boolean isMain) {
        var categories = (isMain != null && isMain) ?
                categoryService.getMainCategory() :
                categoryService.getAllCategories();
        return ResponseEntity.ok(
                new CategoryResponse()
                        .categories(
                                categories.stream()
                                        .map(this::toCategory)
                                        .collect(Collectors.toList())
                        )
        );
    }

    private Category toCategory(CategoryEntity entity) {
        return new Category()
                .id(entity.getId())
                .name(entity.getName())
                .mainCategory(entity.isMainPage())
                .imageUrl(entity.getImageURL());
    }
}
