package ru.skydiver.backend.skydiver.services;


import java.util.List;

import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.model.CategoryEntity;
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
            throw new IllegalArgumentException(); //исключение при добавлении существующей категории
        }
        categoryRepository.addCategory(categoryName);
    }

    public List<CategoryEntity> getAllCategories() {
       return categoryRepository.getAllCategories();
    }
    public List<CategoryEntity> getMainCategory(){
        return categoryRepository.getMainCategory();
    }
}
