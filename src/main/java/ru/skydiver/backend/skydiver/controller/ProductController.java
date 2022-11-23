package ru.skydiver.backend.skydiver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skydiver.backend.skydiver.model.ProductDto;
import ru.skydiver.backend.skydiver.services.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/product/search")
    public List<ProductDto> searchProduct(@RequestParam("searchString") String searchString) {
        return productService.searchProduct(searchString);
    }
}
