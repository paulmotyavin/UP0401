package com.example.praktika.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class JwtRequestInterceptor implements ClientHttpRequestInterceptor {

    private final HttpServletRequest request;

    public JwtRequestInterceptor(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpSession session = this.request.getSession(false);
        String token = (session != null) ? (String) session.getAttribute("token") : null;

        if (token != null) {
            request.getHeaders().set("Authorization", "Bearer " + token);
        }
        return execution.execute(request, body);
    }
}
