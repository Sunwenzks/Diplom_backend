package ru.skydiver.backend.skydiver.services;

import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.model.CategoryDto;
import ru.skydiver.backend.skydiver.model.ProductDto;
import ru.skydiver.backend.skydiver.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductDto> searchProduct(String searchString) {
        return productRepository.searchProduct(searchString);
    }
}
