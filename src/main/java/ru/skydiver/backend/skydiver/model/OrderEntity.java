package ru.skydiver.backend.skydiver.model;

public class OrderEntity {

    private final int id;

    private final String userName;

    private final OrderStatuses status;

    public OrderEntity(int id, String userName, OrderStatuses status) {
        this.id = id;
        this.userName = userName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public OrderStatuses getStatus() {
        return status;
    }
}
