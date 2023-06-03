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

    public void addCategory(String categoryName, boolean mainPage, String imageUrl) {
        categoryRepository.addCategory(
                new CategoryEntity(0, categoryName, mainPage, imageUrl));
    }

    public void removeCategory(int categoryId) {
        categoryRepository.removeCategory(categoryId);
    }

    public List<CategoryEntity> getAllCategories() {
       return categoryRepository.getAllCategories();
    }
    public List<CategoryEntity> getMainCategory(){
        return categoryRepository.getMainCategory();
    }

    public void updateCategory(Integer categoryId, String categoryName, Boolean mainCategory, String url) {
        categoryRepository.updateCategory(
                categoryId,
                categoryName,
                mainCategory,
                url
        );
    }
}
