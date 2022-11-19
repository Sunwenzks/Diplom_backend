package ru.skydiver.backend.skydiver.services;


import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.repositories.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public void addCategory(String categoryName) {
        var existingCategory = categoryRepository.getCategory(categoryName);
        if (existingCategory != null) {
            throw new IllegalArgumentException();
        }
        categoryRepository.addCategory(categoryName);

    }
}
