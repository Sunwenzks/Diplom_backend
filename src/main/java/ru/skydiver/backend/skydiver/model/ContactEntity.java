package ru.skydiver.backend.skydiver.model;

public class ContactEntity {

    private final int id;

    private final String userName;

    private final String contact;

    private final ContactTypes contactType;

    public ContactEntity(
            int id, String userName, String contact, ContactTypes contactType) {
        this.id = id;
        this.userName = userName;
        this.contact = contact;
        this.contactType = contactType;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getContact() {
        return contact;
    }

    public ContactTypes getContactType() {
        return contactType;
    }
}
