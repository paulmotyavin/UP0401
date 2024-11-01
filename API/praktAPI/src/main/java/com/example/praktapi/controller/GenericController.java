package com.example.praktapi.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    @GetMapping
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @GetMapping("/{id}")
    public T findById(@PathVariable ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return getRepository().save(entity);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable ID id, @RequestBody T entity) {
        return getRepository().save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        getRepository().deleteById(id);
    }
}
