package com.example.praktapi.controller;

import com.example.praktapi.model.Sponsor;
import com.example.praktapi.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sponsors")
public class SponsorController extends GenericController<Sponsor, Long> {

    private final SponsorRepository sponsorRepository;

    @Autowired
    public SponsorController(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    protected JpaRepository<Sponsor, Long> getRepository() {
        return sponsorRepository;
    }
}