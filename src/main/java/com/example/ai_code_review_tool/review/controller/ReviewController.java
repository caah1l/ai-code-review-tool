package com.example.ai_code_review_tool.review.controller;

import com.example.ai_code_review_tool.auth.model.User;
import com.example.ai_code_review_tool.auth.repository.UserRepository;
import com.example.ai_code_review_tool.review.model.Review;
import com.example.ai_code_review_tool.review.service.ReviewService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    public ReviewController(ReviewService reviewService, UserRepository userRepository) {
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }

    @GetMapping("/my")
    public List<Review> getMyReviews() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return reviewService.getReviewsByUser(user);
    }
}
