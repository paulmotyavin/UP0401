package com.example.praktapi.controller;

import com.example.praktapi.model.Registration;
import com.example.praktapi.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController extends GenericController<Registration, Long> {

    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationController(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    protected JpaRepository<Registration, Long> getRepository() {
        return registrationRepository;
    }
}