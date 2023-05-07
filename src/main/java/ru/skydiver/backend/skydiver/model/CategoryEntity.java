package ru.skydiver.backend.skydiver.model;
public class CategoryEntity {
    private final int id;
    private final String name;
    private final boolean isMainPage;
    private final String imageURL;
    public CategoryEntity(int id, String name, boolean isMainPage, String imageURL) {
        this.id = id;
        this.name = name;
        this.isMainPage = isMainPage;
        this.imageURL = imageURL;
    }
    public boolean isMainPage() {
        return isMainPage;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public String getImageURL() {
        return imageURL;
    }
}
