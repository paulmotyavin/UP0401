package com.example.praktapi.controller;

import com.example.praktapi.model.Organizer;
import com.example.praktapi.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController extends GenericController<Organizer, Long> {

    private final OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerController(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    protected JpaRepository<Organizer, Long> getRepository() {
        return organizerRepository;
    }
}
