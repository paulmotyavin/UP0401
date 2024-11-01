package com.example.praktika.model;

import jakarta.validation.constraints.Size;

public class Place {

    private Long id;

    @Size(min = 2, max = 70, message="Название должно быть размером от 2 до 70 символов")
    private String name;

    @Size(min = 2, max = 150, message="Адрес должен быть размером от 2 до 150 символов")
    private String address;

    @Size(min = 2, max = 50, message="Контакты должны быть размером от 2 до 50 символов")
    private String contacts;

    public Place() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2, max = 70, message = "Название должно быть размером от 2 до 70 символов") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 70, message = "Название должно быть размером от 2 до 70 символов") String name) {
        this.name = name;
    }

    public @Size(min = 2, max = 150, message = "Адрес должен быть размером от 2 до 150 символов") String getAddress() {
        return address;
    }

    public void setAddress(@Size(min = 2, max = 150, message = "Адрес должен быть размером от 2 до 150 символов") String address) {
        this.address = address;
    }

    public @Size(min = 2, max = 50, message = "Контакты должны быть размером от 2 до 50 символов") String getContacts() {
        return contacts;
    }

    public void setContacts(@Size(min = 2, max = 50, message = "Контакты должны быть размером от 2 до 50 символов") String contacts) {
        this.contacts = contacts;
    }
}
