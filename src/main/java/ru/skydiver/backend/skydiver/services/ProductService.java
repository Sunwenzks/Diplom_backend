package ru.skydiver.backend.skydiver.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.model.ProductEntity;
import ru.skydiver.backend.skydiver.repositories.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductEntity> searchProduct(String searchString) {
        return productRepository.searchProduct(searchString);
    }
    public Optional<ProductEntity> getSearchProduct(Integer productId){
        return productRepository.getSearchProduct(productId);
    }
}
