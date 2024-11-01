package com.example.praktika.model;

import jakarta.validation.constraints.Size;

public class Category {
    private Long id;

    @Size(min = 2, max = 35, message="Категория должна быть размером от 2 до 35 символов")
    private String name;

    private String description;

    public Category() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2, max = 35, message="Категория должна быть размером от 2 до 35 символов") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 35, message="Категория должна быть размером от 2 до 35 символов") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
