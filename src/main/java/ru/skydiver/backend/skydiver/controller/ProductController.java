package ru.skydiver.backend.skydiver.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.skydiver.backend.skydiver.model.ProductDto;
import ru.skydiver.backend.skydiver.services.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin
    @GetMapping("/product/search")
    public List<ProductDto> searchProduct(@RequestParam("searchString") String searchString) {
        return productService.searchProduct(searchString);
    }
    @CrossOrigin
    @GetMapping("/product/info")
    public ProductDto getSearchProduct(@RequestParam("productId") Integer productId){
        return productService.getSearchProduct(productId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
