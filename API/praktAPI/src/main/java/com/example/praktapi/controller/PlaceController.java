package com.example.praktapi.controller;

import com.example.praktapi.model.Place;
import com.example.praktapi.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
public class PlaceController extends GenericController<Place, Long> {

    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    protected JpaRepository<Place, Long> getRepository() {
        return placeRepository;
    }
}