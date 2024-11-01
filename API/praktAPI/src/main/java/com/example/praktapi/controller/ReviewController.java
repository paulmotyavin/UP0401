package com.example.praktapi.controller;

import com.example.praktapi.model.Review;
import com.example.praktapi.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController extends GenericController<Review, Long> {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    protected JpaRepository<Review, Long> getRepository() {
        return reviewRepository;
    }
}