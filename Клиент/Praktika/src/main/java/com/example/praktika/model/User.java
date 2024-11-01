package com.example.praktika.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class User {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 45, message = "Фамилия должна быть размером от 2 до 45 символов")
    private String surname;

    @NotBlank
    @Size(min = 2, max = 35, message = "Имя должно быть размером от 2 до 35 символов")
    private String name;

    @PastOrPresent(message = "Дата не должна превышать сегодняшнюю")
    private LocalDate birth_date;

    @NotBlank
    @Size(min = 2, max = 25, message = "Никнейм должен быть размером от 2 до 25 символов")
    private String username;

    @NotBlank
    private String password;

    @Size(min = 2, max = 25, message = "Роль должна быть размером от 2 до 25 символов")
    private String role;

    private boolean IsActive;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 2, max = 45, message = "Фамилия должна быть размером от 2 до 45 символов") String getSurname() {
        return surname;
    }

    public void setSurname(@NotBlank @Size(min = 2, max = 45, message = "Фамилия должна быть размером от 2 до 45 символов") String surname) {
        this.surname = surname;
    }

    public @NotBlank @Size(min = 2, max = 35, message = "Имя должно быть размером от 2 до 35 символов") String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 2, max = 35, message = "Имя должно быть размером от 2 до 35 символов") String name) {
        this.name = name;
    }

    public @PastOrPresent(message = "Дата не должна превышать сегодняшнюю") LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(@PastOrPresent(message = "Дата не должна превышать сегодняшнюю") LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public @NotBlank @Size(min = 2, max = 25, message = "Никнейм должен быть размером от 2 до 25 символов") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 2, max = 25, message = "Никнейм должен быть размером от 2 до 25 символов") String username) {
        this.username = username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @Size(min = 2, max = 25, message = "Роль должна быть размером от 2 до 25 символов") String getRole() {
        return role;
    }

    public void setRole(@Size(min = 2, max = 25, message = "Роль должна быть размером от 2 до 25 символов") String role) {
        this.role = role;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }
}
