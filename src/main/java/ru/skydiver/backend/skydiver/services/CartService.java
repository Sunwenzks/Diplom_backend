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
        var existingCartRow = cartRepository.getCartRowByUser(userId, productId);
        if (amount <= 0) {
            cartRepository.removeFromCart(userId, productId);
        }
        if (existingCartRow.isEmpty()) {
            cartRepository.addToCart(userId, productId, amount);
        } else {
            cartRepository.updateAmount(
                    userId,
                    productId,
                    existingCartRow.get().amount() + amount);
        }
    }

    public List<CartProductEntity> getUserCart(String userId) {
        return cartRepository.getCartByUser(userId);
    }

    public void changeCart(
            String userId, int productId, int amount) {
        var existingCartRow = cartRepository.getCartRowByUser(userId, productId);
        if (existingCartRow.isEmpty()) {
            return;
        }
        if (amount <= 0) {
            cartRepository.removeFromCart(userId, productId);
        } else {
            cartRepository.updateAmount(
                    userId, productId, amount);
        }
    }

    public void removeAllFromCart(String userId) {
        cartRepository.clearCart(userId);
    }
}
