package com.example.praktika.controller;

import com.example.praktika.model.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN', 'MODER')")
public class ModeratorController {

    private final RestTemplate restTemplate;
    private final String apiUrlCategories = "http://localhost:8088/api/categories";
    private final String apiUrlSponsors = "http://localhost:8088/api/sponsors";
    private final String apiUrlPlaces = "http://localhost:8088/api/places";
    private final String apiUrlOrganizers = "http://localhost:8088/api/organizers";
    private final String apiUrlReviews = "http://localhost:8088/api/reviews";
    private final String apiUrlEvents = "http://localhost:8088/api/events";
    private final String apiUrlUsers = "http://localhost:8088/api/users";
    private final String apiUrlRegistrations = "http://localhost:8088/api/registrations";


    @Autowired
    public ModeratorController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("category/list")
    public String index_categories(Model model) {
        Category[] categories = restTemplate.getForObject(apiUrlCategories, Category[].class);
        model.addAttribute("categories", Arrays.asList(categories));
        return "category/create_category";
    }

    @GetMapping("category/{id}")
    public String show_categories(Model model, @PathVariable("id") long id) {
        Category category = restTemplate.getForObject(apiUrlCategories + "/" + id, Category.class);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }
        model.addAttribute("category", category);
        return "category/detail_category";
    }

    @PostMapping("category/create")
    public String create_categories(@Valid Category category, BindingResult result, RedirectAttributes model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addFlashAttribute("error", errorMessage.toString());
            return "redirect:/category/list";
        }
        restTemplate.postForObject(apiUrlCategories, category, Category.class);
        return "redirect:/category/list";
    }

    @PostMapping("category/edit/{id}")
    public String edit_categories(@Valid Category category, BindingResult result, @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addAttribute("error", errorMessage.toString());
            category.setId(id);
            return "category/detail_category";
        }
        restTemplate.put(apiUrlCategories + "/" + id, category);
        return "redirect:/category/list";
    }

    @PostMapping("category/delete/{id}")
    public String delete_categories(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(apiUrlCategories + "/" + id);
            return "redirect:/category/list";
        } catch (RestClientException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно удалить из-за связанных записей!");
            return "redirect:/category/" + id;
        }
    }

    @GetMapping("category/search")
    public String search_categories(@RequestParam("Name") String name, Model model) {
        List<Category> categories = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(apiUrlCategories, Category[].class)));
        List<Category> foundCategories = categories.stream()
                .filter(category -> category.getName().contains(name))
                .toList();
        model.addAttribute("categories", foundCategories);
        return "category/create_category";
    }

    @GetMapping("sponsor/list")
    public String index(Model model) {
        Sponsor[] sponsors = restTemplate.getForObject(apiUrlSponsors, Sponsor[].class);
        model.addAttribute("sponsors", Arrays.asList(sponsors));
        return "sponsor/create_sponsor";
    }

    @GetMapping("sponsor/{id}")
    public String show(Model model, @PathVariable("id") long id) {
        Sponsor sponsor = restTemplate.getForObject(apiUrlSponsors + "/" + id, Sponsor.class);
        if (sponsor == null) {
            throw new IllegalArgumentException("Sponsor not found");
        }
        model.addAttribute("sponsor", sponsor);
        return "sponsor/detail_sponsor";
    }

    @PostMapping("sponsor/create")
    public String create_sponsors(@Valid Sponsor sponsor, BindingResult result, RedirectAttributes model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addFlashAttribute("error", errorMessage.toString());
            return "redirect:/sponsor/list";
        }
        restTemplate.postForObject(apiUrlSponsors, sponsor, Sponsor.class);
        return "redirect:/sponsor/list";
    }

    @PostMapping("sponsor/edit/{id}")
    public String edit_sponsors(@Valid Sponsor sponsor, BindingResult result, @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addAttribute("error", errorMessage.toString());
            return "sponsor/detail_sponsor";
        }
        restTemplate.put(apiUrlSponsors + "/" + id, sponsor);
        return "redirect:/sponsor/list";
    }

    @PostMapping("sponsor/delete/{id}")
    public String delete_sponsors(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(apiUrlSponsors + "/" + id);
            return "redirect:/sponsor/list";
        } catch (RestClientException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно удалить из-за связанных записей!");
            return "redirect:/sponsor/" + id;
        }
    }

    @GetMapping("sponsor/search")
    public String search_sponsors(@RequestParam("Name") String name, Model model) {
        List<Sponsor> sponsors = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(apiUrlSponsors, Sponsor[].class)));
        List<Sponsor> foundSponsors = sponsors.stream()
                .filter(sponsor -> sponsor.getName().contains(name))
                .toList();
        model.addAttribute("sponsors", foundSponsors);
        return "sponsor/create_sponsor";
    }

    @GetMapping("place/list")
    public String index_places(Model model) {
        Place[] places = restTemplate.getForObject(apiUrlPlaces, Place[].class);
        model.addAttribute("places", Arrays.asList(places));
        return "place/create_place";
    }

    @GetMapping("place/{id}")
    public String show_places(Model model, @PathVariable("id") long id) {
        Place place = restTemplate.getForObject(apiUrlPlaces + "/" + id, Place.class);
        if (place == null) {
            throw new IllegalArgumentException("Place not found");
        }
        model.addAttribute("place", place);
        return "place/detail_place";
    }

    @PostMapping("place/create")
    public String create_places(@Valid Place place, BindingResult result, RedirectAttributes model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addFlashAttribute("error", errorMessage.toString());
            return "redirect:/place/list";
        }
        restTemplate.postForObject(apiUrlPlaces, place, Category.class);
        return "redirect:/place/list";
    }

    @PostMapping("place/edit/{id}")
    public String edit_places(@Valid Place place, BindingResult result, @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addAttribute("error", errorMessage.toString());
            return "place/detail_place";
        }
        restTemplate.put(apiUrlPlaces + "/" + id, place);
        return "redirect:/place/list";
    }

    @PostMapping("place/delete/{id}")
    public String delete_places(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(apiUrlPlaces + "/" + id);
            return "redirect:/place/list";
        } catch (RestClientException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно удалить из-за связанных записей!");
            return "redirect:/place/" + id;
        }
    }

    @GetMapping("place/search")
    public String search_places(@RequestParam("Name") String name, Model model) {
        List<Place> places = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(apiUrlPlaces, Place[].class)));
        List<Place> foundPlaces = places.stream()
                .filter(place -> place.getName().contains(name))
                .toList();
        model.addAttribute("places", foundPlaces);
        return "place/create_place";
    }

    @GetMapping("organizer/list")
    public String index_organizers(Model model) {
        Organizer[] organizers = restTemplate.getForObject(apiUrlOrganizers, Organizer[].class);
        model.addAttribute("organizers", Arrays.asList(organizers));
        return "organizer/create_organizer";
    }

    @GetMapping("organizer/{id}")
    public String show_organizers(Model model, @PathVariable("id") long id) {
        Organizer organizer = restTemplate.getForObject(apiUrlOrganizers + "/" + id, Organizer.class);
        if (organizer == null) {
            throw new IllegalArgumentException("Organizer not found");
        }
        model.addAttribute("organizer", organizer);
        return "organizer/detail_organizer";
    }

    @PostMapping("organizer/create")
    public String create_organizers(@Valid Organizer organizer, BindingResult result, RedirectAttributes model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addFlashAttribute("error", errorMessage.toString());
            return "redirect:/organizer/list";
        }
        restTemplate.postForObject(apiUrlOrganizers, organizer, Organizer.class);
        return "redirect:/organizer/list";
    }

    @PostMapping("organizer/edit/{id}")
    public String edit_organizers(@Valid Organizer organizer, BindingResult result, @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addAttribute("error", errorMessage.toString());
            return "organizer/detail_organizer";
        }
        restTemplate.put(apiUrlOrganizers + "/" + id, organizer);
        return "redirect:/organizer/list";
    }

    @PostMapping("organizer/delete/{id}")
    public String delete_organizers(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(apiUrlOrganizers + "/" + id);
            return "redirect:/organizer/list";
        } catch (RestClientException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно удалить из-за связанных записей!");
            return "redirect:/organizer/" + id;
        }
    }

    @GetMapping("organizer/search")
    public String search_organizers(@RequestParam("Surname") String surname, Model model) {
        List<Organizer> organizers = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(apiUrlOrganizers, Organizer[].class)));
        List<Organizer> foundOrganizers = organizers.stream()
                .filter(organizer -> organizer.getSurname().contains(surname))
                .toList();
        model.addAttribute("organizers", foundOrganizers);
        return "organizer/create_organizer";
    }

    @GetMapping("review/list")
    public String index_reviews(Model model) {
        Review[] reviews = restTemplate.getForObject(apiUrlReviews, Review[].class);
        Event[] events = restTemplate.getForObject(apiUrlEvents, Event[].class);
        User[] users = restTemplate.getForObject(apiUrlUsers, User[].class);
        model.addAttribute("reviews", Arrays.asList(reviews));
        model.addAttribute("events", Arrays.asList(events));
        model.addAttribute("users", Arrays.asList(users));
        return "review/create_review";
    }

    @GetMapping("review/{id}")
    public String show_reviews(Model model, @PathVariable("id") long id) {
        Review review = restTemplate.getForObject(apiUrlReviews + "/" + id, Review.class);
        if (review == null) {
            throw new IllegalArgumentException("Review not found");
        }

        Event[] events = restTemplate.getForObject(apiUrlEvents, Event[].class);
        User[] users = restTemplate.getForObject(apiUrlUsers, User[].class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDate = review.getDate().format(formatter);

        model.addAttribute("events", Arrays.asList(events));
        model.addAttribute("users", Arrays.asList(users));
        model.addAttribute("review", review);
        model.addAttribute("formattedDate", formattedDate);

        return "review/detail_review";
    }

    @PostMapping("review/create")
    public String create_reviews(@Valid Review review, BindingResult result, @RequestParam long eventId, @RequestParam long userId,
                                 RedirectAttributes model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addFlashAttribute("error", errorMessage.toString());
            return "redirect:/review/list";
        }

        Event event = restTemplate.getForObject(apiUrlEvents + "/" + eventId, Event.class);
        User user = restTemplate.getForObject(apiUrlUsers + "/" + userId, User.class);

        review.setEvent(event);
        review.setUser(user);
        review.setDate(LocalDateTime.now());


        restTemplate.postForObject(apiUrlReviews, review, Registration.class);
        return "redirect:/review/list";
    }

    @PostMapping("review/edit/{id}")
    public String edit_reviews(@Valid Review review, BindingResult result, @PathVariable("id") long id,
                       @RequestParam long eventId, @RequestParam long userId, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addAttribute("error", errorMessage.toString());
            return "review/detail_review";
        }

        Event event = restTemplate.getForObject(apiUrlEvents + "/" + eventId, Event.class);
        User user = restTemplate.getForObject(apiUrlUsers + "/" + userId, User.class);

        review.setEvent(event);
        review.setUser(user);
        review.setDate(LocalDateTime.now());

        restTemplate.put(apiUrlReviews + "/" + id, review);
        return "redirect:/review/list";
    }

    @PostMapping("review/delete/{id}")
    public String delete_reviews(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(apiUrlReviews + "/" + id);
            return "redirect:/review/list";
        } catch (RestClientException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно удалить из-за связанных записей!");
            return "redirect:/review/" + id;
        }
    }

    @GetMapping("review/search")
    public String search_reviews(@RequestParam("Name") String name, Model model) {
        List<Review> reviews = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(apiUrlReviews, Review[].class)));
        List<Review> foundReviews = reviews.stream().filter(reg -> reg.getEvent().getName().contains(name)).toList();
        Event[] events = restTemplate.getForObject(apiUrlEvents, Event[].class);
        User[] users = restTemplate.getForObject(apiUrlUsers, User[].class);
        model.addAttribute("events", Arrays.asList(events));
        model.addAttribute("users", Arrays.asList(users));
        model.addAttribute("reviews", foundReviews);
        return "review/create_review";
    }

    @PostMapping("event/create")
    public String create_events(@Valid Event event, BindingResult result, @RequestParam("categoryId") long categoryId,
                         @RequestParam long organizerId, @RequestParam long placeId, @RequestParam long sponsorId,
                         RedirectAttributes model) {
        LocalDate date = event.getDate();
        event.setDate(date);
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addFlashAttribute("error", errorMessage.toString());
            return "redirect:/event/list";
        }

        Category category = restTemplate.getForObject(apiUrlCategories + "/" + categoryId, Category.class);
        Organizer organizer = restTemplate.getForObject(apiUrlOrganizers + "/" + organizerId, Organizer.class);
        Place place = restTemplate.getForObject(apiUrlPlaces + "/" + placeId, Place.class);
        Sponsor sponsor = restTemplate.getForObject(apiUrlSponsors + "/" + sponsorId, Sponsor.class);

        event.setCategory(category);
        event.setOrganizer(organizer);
        event.setPlace(place);
        event.setSponsor(sponsor);

        restTemplate.postForObject(apiUrlEvents, event, Event.class);
        return "redirect:/event/list";
    }

    @PostMapping("event/edit/{id}")
    public String edit_events(@Valid Event event, BindingResult result, @PathVariable("id") long id, @RequestParam("categoryId") long categoryId,
                       @RequestParam long organizerId, @RequestParam long placeId, @RequestParam long sponsorId, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addAttribute("error", errorMessage.toString());
            return "event/detail_event";
        }

        Category category = restTemplate.getForObject(apiUrlCategories + "/" + categoryId, Category.class);
        Organizer organizer = restTemplate.getForObject(apiUrlOrganizers + "/" + organizerId, Organizer.class);
        Place place = restTemplate.getForObject(apiUrlPlaces + "/" + placeId, Place.class);
        Sponsor sponsor = restTemplate.getForObject(apiUrlSponsors + "/" + sponsorId, Sponsor.class);

        event.setCategory(category);
        event.setOrganizer(organizer);
        event.setPlace(place);
        event.setSponsor(sponsor);

        restTemplate.put(apiUrlEvents + "/" + id, event);
        return "redirect:/event/list";
    }

    @PostMapping("event/delete/{id}")
    public String delete_events(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(apiUrlEvents + "/" + id);
            return "redirect:/event/list";
        } catch (RestClientException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно удалить из-за связанных записей!");
            return "redirect:/event/" + id;
        }
    }

    @GetMapping("registration/list")
    public String index_registrations(Model model) {
        Registration[] registrations = restTemplate.getForObject(apiUrlRegistrations, Registration[].class);
        Event[] events = restTemplate.getForObject(apiUrlEvents, Event[].class);
        User[] users = restTemplate.getForObject(apiUrlUsers, User[].class);
        model.addAttribute("registrations", Arrays.asList(registrations));
        model.addAttribute("events", Arrays.asList(events));
        model.addAttribute("users", Arrays.asList(users));
        return "registration/create_registration";
    }

    @GetMapping("registration/{id}")
    public String show_registrations(Model model, @PathVariable("id") long id) {
        Registration registration = restTemplate.getForObject(apiUrlRegistrations + "/" + id, Registration.class);
        if (registration == null) {
            throw new IllegalArgumentException("Registration not found");
        }

        Event[] events = restTemplate.getForObject(apiUrlEvents, Event[].class);
        User[] users = restTemplate.getForObject(apiUrlUsers, User[].class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDate = registration.getRegistration_date().format(formatter);

        model.addAttribute("events", Arrays.asList(events));
        model.addAttribute("users", Arrays.asList(users));
        model.addAttribute("registration", registration);
        model.addAttribute("formattedDate", formattedDate);

        return "registration/detail_registration";
    }

    @PostMapping("registration/create")
    public String create_registrations(@Valid Registration registration, BindingResult result, @RequestParam long eventId, @RequestParam long userId,
                         RedirectAttributes model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addFlashAttribute("error", errorMessage.toString());
            return "redirect:/registration/list";
        }

        Event event = restTemplate.getForObject(apiUrlEvents + "/" + eventId, Event.class);
        User user = restTemplate.getForObject(apiUrlUsers + "/" + userId, User.class);
        System.out.println(user.getSurname());
        Registration[] registrations = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(apiUrlRegistrations, Registration[].class)))
                .filter(reg -> reg.getUser().getId().equals(user.getId())).toArray(Registration[]::new);
        System.out.println(Arrays.toString(registrations));
        if (registrations.length == 0) {
            registration.setEvent(event);
            registration.setUser(user);
            registration.setRegistration_date(LocalDateTime.now());

            restTemplate.postForObject(apiUrlRegistrations, registration, Registration.class);
            return "redirect:/registration/list";
        } else{
            model.addFlashAttribute("error", "Пользователь уже зарегистрирован на данное событие");
            return "redirect:/registration/list";
        }
    }

    @PostMapping("registration/edit/{id}")
    public String edit_registrations(@Valid Registration registration, BindingResult result, @PathVariable("id") long id,
                       @RequestParam long eventId, @RequestParam long userId, RedirectAttributes model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addAttribute("error", errorMessage.toString());
            return "registration/detail_registration";
        }

        Event event = restTemplate.getForObject(apiUrlEvents + "/" + eventId, Event.class);
        User user = restTemplate.getForObject(apiUrlUsers + "/" + userId, User.class);
        System.out.println(user.getSurname());
        Registration[] registrations = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(apiUrlRegistrations, Registration[].class)))
                .filter(reg -> reg.getUser().getId().equals(user.getId())).toArray(Registration[]::new);
        if (registrations.length == 0) {
            registration.setEvent(event);
            registration.setUser(user);
            registration.setRegistration_date(LocalDateTime.now());

            restTemplate.postForObject(apiUrlRegistrations, registration, Registration.class);
            return "redirect:/registration/list";
        } else{
            model.addFlashAttribute("error", "Пользователь уже зарегистрирован на данное событие");
            return "redirect:/registration/" + id;
        }
    }

    @PostMapping("registration/delete/{id}")
    public String delete_registrations(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(apiUrlRegistrations + "/" + id);
            return "redirect:/registration/list";
        } catch (RestClientException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно удалить из-за связанных записей!");
            return "redirect:/registration/" + id;
        }
    }

    @GetMapping("registration/search")
    public String search_registrations(@RequestParam("Name") String name, Model model) {
        List<Registration> regs = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(apiUrlRegistrations, Registration[].class)));
        List<Registration> foundRegistrations = regs.stream().filter(reg -> reg.getEvent().getName().contains(name)).toList();
        Event[] events = restTemplate.getForObject(apiUrlEvents, Event[].class);
        User[] users = restTemplate.getForObject(apiUrlUsers, User[].class);
        model.addAttribute("events", Arrays.asList(events));
        model.addAttribute("users", Arrays.asList(users));
        model.addAttribute("registrations", foundRegistrations);
        return "registration/create_registration";
    }
}
