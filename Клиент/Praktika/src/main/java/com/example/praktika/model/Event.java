package com.example.praktika.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class Event {

    private Long id;

    @Size(min = 2, max = 50, message = "Название должно быть размером от 2 до 50 символов")
    private String name;

    @FutureOrPresent(message = "Дата не должна быть меньше сегодняшней")
    private LocalDate date;

    @Size(min = 10,max = 1000, message = "Описание должно быть размером от 10 до 1000 символов")
    private String description;

    private Category category;

    private Place place;

    private Organizer organizer;

    private Sponsor sponsor;

    @Size(min = 2, max = 3, message = "Цензура должна быть представлена в двух или трех символах")
    private String censorship;

    public Event() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2, max = 50, message = "Название должно быть размером от 2 до 50 символов") String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 50, message = "Название должно быть размером от 2 до 50 символов") String name) {
        this.name = name;
    }

    public @FutureOrPresent(message = "Дата не должна быть меньше сегодняшней") LocalDate getDate() {
        return date;
    }

    public void setDate(@FutureOrPresent(message = "Дата не должна быть меньше сегодняшней") LocalDate date) {
        this.date = date;
    }

    public @Size(min = 10, max = 1000, message = "Описание должно быть размером от 10 до 1000 символов") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, max = 1000, message = "Описание должно быть размером от 10 до 1000 символов") String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public @Size(min = 2, max = 3, message = "Цензура должна быть представлена в двух или трех символах") String getCensorship() {
        return censorship;
    }

    public void setCensorship(@Size(min = 2, max = 3, message = "Цензура должна быть представлена в двух или трех символах") String censorship) {
        this.censorship = censorship;
    }
}
