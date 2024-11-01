package com.example.praktika.controller;

import com.example.praktika.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AnonymousController {
    private final RestTemplate restTemplate;
    private final String apiUrl = "http://localhost:8088/api/users";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AnonymousController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletRequest request) {
        String url = "http://localhost:8088/login";

        try {
            HttpEntity<User> requestEntity = new HttpEntity<>(user);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String token = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                HttpSession session = request.getSession();
                session.setAttribute("token", token);

                model.addAttribute("message", "Успешная авторизация");
                return "redirect:/";
            } else {
                model.addAttribute("message", "Ошибка: " + response.getBody());
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка: " + e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Некорректные данные");
            return "register";
        }

        if (!user.getUsername().matches("^[a-zA-Z0-9]*$") || !user.getPassword().matches("^[a-zA-Z0-9]*$")) {
            model.addAttribute("message", "Неверный формат логина или пароля");
            return "register";
        }

        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        restTemplate.postForObject(apiUrl, user, User.class);

        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "home";
    }

    @GetMapping("/not_found")
    public String notFound(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");

        model.addAttribute("role", role);
        return "not_found";
    }

    @GetMapping("/")
    String home(Model model, HttpServletRequest request) {
        String username = "anonymousUser";
        String role = "anonymousUser";

        HttpSession session = request.getSession(false);
        String token = (session != null) ? (String) session.getAttribute("token") : null;
        if (token != null) {

            try {
                ResponseEntity<User> response = restTemplate.exchange(
                        "http://localhost:8088/current-user",
                        HttpMethod.GET,
                        new HttpEntity<>(createHeaders("Bearer " + token)),
                        User.class
                );
                if (response.getStatusCode() == HttpStatus.OK) {
                    User userResponse = response.getBody();
                    if (userResponse != null) {
                        username = userResponse.getUsername();
                        role = userResponse.getRole();
                    }
                } else {
                    System.out.println("Ошибка при получении данных пользователя: " + response.getStatusCode());
                }
            } catch (RestClientException e) {
                System.err.println("Ошибка при вызове API: " + e.getMessage());
            }
        }
        model.addAttribute("user", username);
        model.addAttribute("role", role);
        return "home";
    }

    private HttpHeaders createHeaders(String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        return headers;
    }
}
