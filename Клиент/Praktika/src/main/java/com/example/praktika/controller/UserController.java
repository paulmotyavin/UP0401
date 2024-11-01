package com.example.praktika.controller;

import com.example.praktika.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@PreAuthorize("hasAnyAuthority('USER', 'MODER', 'ADMIN')")
public class UserController {
    private final RestTemplate restTemplate;
    private final String apiUrlUsers = "http://localhost:8088/api/users";
    private final String apiUrlEvents = "http://localhost:8088/api/events";
    private final String apiUrlReviews = "http://localhost:8088/api/reviews";
    private final String apiUrlCategory = "http://localhost:8088/api/categories";
    private final String apiUrlOrganizer = "http://localhost:8088/api/organizers";
    private final String apiUrlPlace = "http://localhost:8088/api/places";
    private final String apiUrlSponsor = "http://localhost:8088/api/sponsors";
    private final String apiUrlRegistrations = "http://localhost:8088/api/registrations";


    @Autowired
    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/event/review/add/{id}")
    public String write_review(@RequestParam("comment") String comment, @PathVariable("id") long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        if (username != null) {
            User user = Arrays.stream(restTemplate.getForObject(apiUrlUsers, User[].class))
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
            Event event = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(apiUrlEvents, Event[].class))).findFirst().filter(e -> e.getId().equals(id)).orElse(null);;
            Review review = new Review();
            review.setComment(comment);
            review.setUser(user);
            review.setEvent(event);
            review.setDate(LocalDateTime.now());
            restTemplate.postForObject(apiUrlReviews, review, Review.class);
            return "redirect:/event/" + id;
        }
        return "redirect:/";
    }

    @GetMapping("event/list")
    public String index_events(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = Arrays.stream(restTemplate.getForObject(apiUrlUsers, User[].class))
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");

        if (role.equals("USER")) {
            int age = Period.between(user.getBirth_date(), LocalDate.now()).getYears();

            Event[] filteredEvents = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(apiUrlEvents, Event[].class)))
                    .filter(event -> {
                        String censorship = event.getCensorship().replace("+", "");
                        int requiredAge = Integer.parseInt(censorship);
                        return age >= requiredAge;
                    })
                    .toArray(Event[]::new);

            model.addAttribute("events", Arrays.asList(filteredEvents));
            model.addAttribute("role", "USER");
            return "event/create_event";

        } else{
            Event[] events = restTemplate.getForObject(apiUrlEvents, Event[].class);
            Category[] categories = restTemplate.getForObject(apiUrlCategory, Category[].class);
            Organizer[] organizers = restTemplate.getForObject(apiUrlOrganizer, Organizer[].class);
            Place[] places = restTemplate.getForObject(apiUrlPlace, Place[].class);
            Sponsor[] sponsors = restTemplate.getForObject(apiUrlSponsor, Sponsor[].class);
            model.addAttribute("events", Arrays.asList(events));
            model.addAttribute("categories", Arrays.asList(categories));
            model.addAttribute("organizers", Arrays.asList(organizers));
            model.addAttribute("places", Arrays.asList(places));
            model.addAttribute("sponsors", Arrays.asList(sponsors));
            model.addAttribute("events", Arrays.asList(events));
        }

        return "event/create_event";
    }

    @GetMapping("event/{id}")
    public String show_event(Model model, @PathVariable("id") long id) {
        Event event = restTemplate.getForObject(apiUrlEvents + "/" + id, Event.class);
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = Arrays.stream(restTemplate.getForObject(apiUrlUsers, User[].class))
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);

        Registration[] registrations = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(apiUrlRegistrations, Registration[].class)))
                .filter(reg -> reg.getUser().getId().equals(user.getId())).toArray(Registration[]::new);



        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).findFirst()
                .orElse("UNKNOWN");

        Category[] categories = restTemplate.getForObject(apiUrlCategory, Category[].class);
        Organizer[] organizers = restTemplate.getForObject(apiUrlOrganizer, Organizer[].class);
        Place[] places = restTemplate.getForObject(apiUrlPlace, Place[].class);
        Sponsor[] sponsors = restTemplate.getForObject(apiUrlSponsor, Sponsor[].class);
        Review[] reviews = restTemplate.getForObject(apiUrlReviews, Review[].class);
        Review[] foundReviews = Arrays.stream(reviews).toList().stream().filter(review -> review.getEvent().getId() == id).toArray(Review[]::new);
        model.addAttribute("categories", Arrays.asList(categories));
        model.addAttribute("organizers", Arrays.asList(organizers));
        model.addAttribute("places", Arrays.asList(places));
        model.addAttribute("sponsors", Arrays.asList(sponsors));
        model.addAttribute("reviews", Arrays.asList(foundReviews));
        model.addAttribute("event", event);
        model.addAttribute("role", role);
        model.addAttribute("registrations", registrations);

        return "event/detail_event";
    }

    @GetMapping("event/search")
    public String search_events(@RequestParam("Name") String name, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = Arrays.stream(restTemplate.getForObject(apiUrlUsers, User[].class))
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);


        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");

        if (role.equals("USER")) {
            int age = Period.between(user.getBirth_date(), LocalDate.now()).getYears();

            List<Event> filteredEvents = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(apiUrlEvents, Event[].class)))
                    .filter(event -> {
                        String censorship = event.getCensorship().replace("+", "");
                        int requiredAge = Integer.parseInt(censorship);
                        return age >= requiredAge;
                    })
                    .toList();

            List<Event> foundedEvents = filteredEvents.stream().filter(event -> event.getName().contains(name)).toList();

            model.addAttribute("events", foundedEvents);
            model.addAttribute("role", "USER");
            return "event/create_event";

        } else{
            List<Event> events = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(apiUrlEvents, Event[].class)));
            List<Event> foundEvents = events.stream().filter(event -> event.getName().contains(name)).toList();
            Category[] categories = restTemplate.getForObject(apiUrlCategory, Category[].class);
            Organizer[] organizers = restTemplate.getForObject(apiUrlOrganizer, Organizer[].class);
            Place[] places = restTemplate.getForObject(apiUrlPlace, Place[].class);
            Sponsor[] sponsors = restTemplate.getForObject(apiUrlSponsor, Sponsor[].class);
            model.addAttribute("categories", Arrays.asList(categories));
            model.addAttribute("organizers", Arrays.asList(organizers));
            model.addAttribute("places", Arrays.asList(places));
            model.addAttribute("sponsors", Arrays.asList(sponsors));
            model.addAttribute("events", foundEvents);
            return "event/create_event";
        }
    }

    @PostMapping("event/reg/{id}")
    public String register_event(@PathVariable long id,RedirectAttributes model) {
        Event event = restTemplate.getForObject(apiUrlEvents + "/" + id, Event.class);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = Arrays.stream(restTemplate.getForObject(apiUrlUsers, User[].class))
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        Registration[] registrations = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(apiUrlRegistrations, Registration[].class)))
                .filter(reg -> reg.getUser().getId().equals(user.getId()))
                .filter(reg -> reg.getEvent().getId().equals(id))
                .toArray(Registration[]::new);

        if (registrations.length > 0) {
            model.addFlashAttribute("error", "Вы уже зарегистрированы на это событие.");
            return "redirect:/event/" + id;
        }

        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setUser(user);
        registration.setRegistration_date(LocalDateTime.now());

        restTemplate.postForObject(apiUrlRegistrations, registration, Registration.class);
        return "redirect:/event/" + id;
    }

    @PostMapping("event/cancel/{id}")
    public String cancelRegistration(@PathVariable long id, RedirectAttributes model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = Arrays.stream(restTemplate.getForObject(apiUrlUsers, User[].class))
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        Registration[] registrations = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(apiUrlRegistrations, Registration[].class)))
                .filter(reg -> reg.getUser().getId().equals(user.getId()))
                .filter(reg -> reg.getEvent().getId().equals(id))
                .toArray(Registration[]::new);

        if (registrations.length == 0) {
            model.addFlashAttribute("error", "Регистрация не найдена.");
            return "redirect:/event/" + id;
        }

        for (Registration registration : registrations) {
            restTemplate.delete(apiUrlRegistrations + "/" + registration.getId());
        }

        model.addFlashAttribute("success", "Регистрация успешно отменена.");
        return "redirect:/event/" + id;
    }
}
