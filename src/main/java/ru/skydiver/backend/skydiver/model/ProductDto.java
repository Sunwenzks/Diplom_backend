package ru.skydiver.backend.skydiver.model;

public class ProductDto {
    private final int id_product;
    private final String name;
    private final int id_category;
    private final int price;

    public ProductDto(int id_product, String name, int id_category, int price) {
        this.id_product = id_product;
        this.name = name;
        this.id_category = id_category;
        this.price = price;
    }

    public int getIdProduct() {
        return id_product;
    }

    public String getName() {
        return name;
    }

    public int getIdCategory() {
        return id_category;
    }

    public int getPrice() {
        return price;
    }
}
