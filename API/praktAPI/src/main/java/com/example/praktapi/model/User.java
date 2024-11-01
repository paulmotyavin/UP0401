package com.example.praktapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 45)
    private String surname;

    @NotBlank
    @Size(min = 2, max = 35)
    private String name;

    private LocalDate birth_date;

    @NotBlank
    @Size(min = 2, max = 25)
    private String username;

    @NotBlank
    private String password;

    @Size(min = 2, max = 25)
    private String role;

    private boolean IsActive;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 2, max = 45) String getSurname() {
        return surname;
    }

    public void setSurname(@NotBlank @Size(min = 2, max = 45) String surname) {
        this.surname = surname;
    }

    public @NotBlank @Size(min = 2, max = 35) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 2, max = 35) String name) {
        this.name = name;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public @NotBlank @Size(min = 2, max = 25) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 2, max = 25) String username) {
        this.username = username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @Size(min = 2, max = 25) String getRole() {
        return role;
    }

    public void setRole(@Size(min = 2, max = 25) String role) {
        this.role = role;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
