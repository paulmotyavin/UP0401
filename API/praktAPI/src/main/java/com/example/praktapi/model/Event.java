package com.example.praktapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50)
    private String name;

    private LocalDate date;

    @Size(min = 10,max = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_place")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "id_organizer")
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "id_sponsor")
    private Sponsor sponsor;

    @Size(min = 2, max = 3)
    private String censorship;

    public Event() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2, max = 50) String getName() {
        return name;
    }

    public void setName(@Size(min = 2, max = 50) String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public @Size(min = 10, max = 1000) String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, max = 1000) String description) {
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

    public @Size(min = 2, max = 3) String getCensorship() {
        return censorship;
    }

    public void setCensorship(@Size(min = 2, max = 3) String censorship) {
        this.censorship = censorship;
    }
}