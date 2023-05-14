package ru.skydiver.backend.skydiver.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skydiver.backend.skydiver.model.CartProductEntity;
import ru.skydiver.backend.skydiver.repositories.CartRepository;
import ru.skydiver.backend.skydiver.repositories.OrderRepository;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository
                        ) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void createOrder(String userId) {
        var userCart = cartRepository.getCartByUser(userId);
        if (userCart.isEmpty()) {
            return;
        }
        var orderId = orderRepository.createOrder(userId);
        orderRepository.addItemsToOrder(
                orderId,
                userCart.stream()
                        .collect(Collectors.toMap(
                                CartProductEntity::productId,
                                CartProductEntity::amount)));
        cartRepository.clearCart(userId);
    }
}
