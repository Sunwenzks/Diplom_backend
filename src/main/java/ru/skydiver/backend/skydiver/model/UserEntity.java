package ru.skydiver.backend.skydiver.model;

public class UserEntity {
    private final int id;
    private final String name;
    private final boolean enabled;

    public UserEntity(int id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getEnabled() {
        return enabled;
    }
}
