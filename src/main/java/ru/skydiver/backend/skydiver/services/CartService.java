package ru.skydiver.backend.skydiver.services;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.skydiver.backend.skydiver.model.CartProductEntity;
import ru.skydiver.backend.skydiver.repositories.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(String userId, int productId, int amount) {
        cartRepository.addToCart(userId, productId, amount);
    }

    public List<CartProductEntity> getUserCart(String userId) {
        return cartRepository.getCartByUser(userId);
    }
}
