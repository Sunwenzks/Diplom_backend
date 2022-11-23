package ru.skydiver.backend.skydiver.model;


public class CategoryDto {

    private final int id;

    private final String name;

    private final boolean isMainPage;


    public CategoryDto(int id, String name, boolean isMainPage) {
        this.id = id;
        this.name = name;
        this.isMainPage = isMainPage;
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
}
