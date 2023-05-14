package ru.skydiver.backend.skydiver.controller;

import org.openapitools.api.OrderApi;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import ru.skydiver.backend.skydiver.services.OrderService;

@RestController
public class OrderController  implements OrderApi {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Void> createOrder() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.createOrder(userName);
        return ResponseEntity.ok(null);
    }
}
