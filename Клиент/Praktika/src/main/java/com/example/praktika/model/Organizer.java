package com.example.praktika.model;

import jakarta.validation.constraints.Size;

public class Organizer {

    private Long id;

    @Size(min = 2,max = 45, message="Фамилия должна быть размером от 2 до 45 символов")
    private String surname;

    @Size(min = 2,max = 35, message="Имя должно быть размером от 2 до 35 символов")
    private String name;

    @Size(min = 2,max = 50, message="Контакты должны быть размером от 2 до 50 символов")
    private String contacts;

    public Organizer() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2, max = 45, message = "Фамилия должна быть размером от 2 до 45 символов") String getSurname() {
        return surname;
    }

    public void setSurname(@Size(min = 2, max = 45, message = "Фамилия должна быть размером от 2 до 45 символов") String surname) {
        this.surname = surname;
    }

    public @Size(min = 2, max = 35, message = "Имя должно быть размером от 2 до 35 символов") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 35, message = "Имя должно быть размером от 2 до 35 символов") String name) {
        this.name = name;
    }

    public @Size(min = 2, max = 50, message = "Контакты должны быть размером от 2 до 50 символов") String getContacts() {
        return contacts;
    }

    public void setContacts(@Size(min = 2, max = 50, message = "Контакты должны быть размером от 2 до 50 символов") String contacts) {
        this.contacts = contacts;
    }
}
