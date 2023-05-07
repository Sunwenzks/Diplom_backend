package ru.skydiver.backend.skydiver.controller;

import java.util.stream.Collectors;

import org.openapitools.api.ProductApi;
import org.openapitools.model.Product;
import org.openapitools.model.ProductSearchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.skydiver.backend.skydiver.model.ProductEntity;
import ru.skydiver.backend.skydiver.services.ProductService;

@RestController
public class ProductController implements ProductApi {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductSearchResponse> productSearchGet(String searchString) {
        return ResponseEntity.ok(
                new ProductSearchResponse().products(
                        productService.searchProduct(searchString)
                                .stream()
                                .map(this::toProduct)
                                .collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<Product> productInfoGet(Integer productId) {
        return ResponseEntity.ok(
                productService.getSearchProduct(productId)
                        .map(this::toProduct)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Override
    public ResponseEntity<ProductSearchResponse> productListGet(Integer category, Integer priceFrom, Integer priceTo,
                                                                String searchString) {
        var products =
                productService.searchProduct(category, priceFrom, priceTo, searchString)
                        .stream()
                        .map(this::toProduct)
                        .toList();
        return ResponseEntity.ok(new ProductSearchResponse().products(products));
    }

    private Product toProduct(ProductEntity entity) {
        return new Product()
                .id(entity.getIdProduct())
                .name(entity.getName())
                .categoryId(entity.getIdCategory())
                .productImage(entity.getProductURL())
                .productDescription(entity.getProduct_description())
                .price(entity.getPrice());
    }
}
