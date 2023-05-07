package ru.skydiver.backend.skydiver.controller;

import org.openapitools.api.CartApi;
import org.openapitools.model.CartProduct;
import org.openapitools.model.CartRequest;
import org.openapitools.model.CartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import ru.skydiver.backend.skydiver.model.CartProductEntity;
import ru.skydiver.backend.skydiver.services.CartService;

@RestController
public class CartController implements CartApi {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<Void> addToCart(CartRequest cartRequest) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.addToCart(userName, cartRequest.getProductId(), cartRequest.getAmount());
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<CartResponse> getCart() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var cart = cartService.getUserCart(userName)
                .stream()
                .map(CartController::toCartResponse)
                .toList();
        return ResponseEntity.ok(new CartResponse().products(cart));
    }

    @Override
    public ResponseEntity<Void> removeFromCart(CartRequest cartRequest) {
        return CartApi.super.removeFromCart(cartRequest);
    }

    private static CartProduct toCartResponse(CartProductEntity entity) {
        return new CartProduct()
                .id(entity.productId())
                .amount(entity.amount())
                .name(entity.productName())
                .price(entity.price())
                .productImage(entity.imageUrl());
    }
}
