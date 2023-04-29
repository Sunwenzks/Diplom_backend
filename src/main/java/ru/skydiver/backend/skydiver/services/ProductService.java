package ru.skydiver.backend.skydiver.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.model.CategoryDto;
import ru.skydiver.backend.skydiver.model.ProductDto;
import ru.skydiver.backend.skydiver.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductDto> searchProduct(String searchString) {
        return productRepository.searchProduct(searchString);
    }
    public Optional<ProductDto> getSearchProduct(Integer productId){
        return productRepository.getSearchProduct(productId);
    }
}
