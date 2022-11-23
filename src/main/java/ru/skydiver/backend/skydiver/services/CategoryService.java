package ru.skydiver.backend.skydiver.services;


import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.model.CategoryDto;
import ru.skydiver.backend.skydiver.repositories.CategoryRepository;

import java.util.List;

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

    public List<CategoryDto> getAllCategories() {
       return categoryRepository.getAllCategories();
    }
    public List<CategoryDto> getMainCategory(){
        return categoryRepository.getMainCategory();
    }
}
