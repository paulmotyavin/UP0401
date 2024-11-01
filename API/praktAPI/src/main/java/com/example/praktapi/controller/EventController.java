package com.example.praktapi.controller;

import com.example.praktapi.model.Event;
import com.example.praktapi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController extends GenericController<Event, Long> {

    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    protected JpaRepository<Event, Long> getRepository() {
        return eventRepository;
    }
}
