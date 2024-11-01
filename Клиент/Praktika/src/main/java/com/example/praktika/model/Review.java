package com.example.praktika.model;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class Review {

    private Long id;

    private User user;

    @Size(min = 5,max = 1000, message = "Комментарий должен быть размером от 5 до 1000 символов")
    private String comment;

    private LocalDateTime date;

    private Event event;

    public Review() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @Size(min = 5,max = 1000, message = "Комментарий должен быть размером от 5 до 1000 символов") String getComment() {
        return comment;
    }

    public void setComment(@Size(min = 5,max = 1000, message = "Комментарий должен быть размером от 5 до 1000 символов") String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
