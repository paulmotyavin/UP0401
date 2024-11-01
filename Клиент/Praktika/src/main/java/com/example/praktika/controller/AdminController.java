package com.example.praktika.controller;

import com.example.praktika.model.Organizer;
import com.example.praktika.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate;
    private final String apiUrl = "http://localhost:8088/api/users";

    @Autowired
    public AdminController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("user/list")
    public String index(Model model) {
        User[] users = restTemplate.getForObject(apiUrl, User[].class);
        model.addAttribute("users", Arrays.asList(users));
        return "user/create_user";
    }

    @GetMapping("user/{id}")
    public String show(Model model, @PathVariable("id") long id) {
        User user = restTemplate.getForObject(apiUrl + "/" + id, User.class);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        model.addAttribute("user", user);
        return "user/detail_user";
    }

    @PostMapping("user/create")
    public String create(@Valid User user, BindingResult result, RedirectAttributes model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addFlashAttribute("error", errorMessage.toString());
            return "redirect:/user/list";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        restTemplate.postForObject(apiUrl, user, Organizer.class);
        return "redirect:/user/list";
    }

    @PostMapping("user/edit/{id}")
    public String edit(@Valid User user, BindingResult result, @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();

            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            model.addAttribute("error", errorMessage.toString());
            return "user/detail_user";
        }
        restTemplate.put(apiUrl + "/" + id, user);
        return "redirect:/user/list";
    }

    @PostMapping("user/delete/{id}")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(apiUrl + "/" + id);
            return "redirect:/user/list";
        } catch (RestClientException e) {
            redirectAttributes.addFlashAttribute("error", "Невозможно удалить. Возможно, есть связанные записи.");
            return "redirect:/user/" + id;
        }
    }

    @GetMapping("user/search")
    public String search(@RequestParam("Surname") String surname, Model model) {
        List<User> users = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(apiUrl, User[].class)));
        List<User> foundUsers = users.stream()
                .filter(user -> user.getSurname().contains(surname))
                .toList();
        model.addAttribute("users", foundUsers);
        return "user/create_user";
    }
}
