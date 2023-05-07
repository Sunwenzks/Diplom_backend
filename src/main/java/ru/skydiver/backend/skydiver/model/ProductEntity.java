package ru.skydiver.backend.skydiver.model;

public class ProductEntity {
    private final int id_product;
    private final String name;
    private final int id_category;
    private final int price;
   private final String productURL;
   private final String product_description;
  public ProductEntity(int id_product, String name, int id_category, int price, String productURL, String product_description) {
        this.id_product = id_product;
        this.name = name;
        this.id_category = id_category;
        this.price = price;
        this.productURL = productURL;
        this.product_description = product_description;
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
    public String getProductURL() {
        return productURL;
    }
    public String getProduct_description() {
        return product_description;
    }
}
