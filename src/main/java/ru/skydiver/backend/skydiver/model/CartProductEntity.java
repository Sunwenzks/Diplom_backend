package ru.skydiver.backend.skydiver.model;

public record CartProductEntity(String userId, int productId, int amount, String productName, String imageUrl,
                                int price) {

}
