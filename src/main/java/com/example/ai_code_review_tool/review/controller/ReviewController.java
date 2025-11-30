package com.example.ai_code_review_tool.review.controller;

import com.example.ai_code_review_tool.ai.dto.AiReviewDTO;
import com.example.ai_code_review_tool.auth.model.User;
import com.example.ai_code_review_tool.auth.repository.UserRepository;
import com.example.ai_code_review_tool.review.dto.ReviewDetailDTO;
import com.example.ai_code_review_tool.review.model.Review;
import com.example.ai_code_review_tool.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public ReviewController(ReviewService reviewService, UserRepository userRepository,ObjectMapper objectMapper) {
        this.reviewService = reviewService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/my")
    public List<Review> getMyReviews() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return reviewService.getReviewsByUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDetailDTO> getReviewDetails(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        Review review = reviewService.getReviewById(id, user)
                .orElseThrow(() -> new RuntimeException("Review not found or unauthorized"));

        try {
            AiReviewDTO aiReview = new AiReviewDTO(
                    review.getSummary(),
                    objectMapper.readValue(review.getScopeOfImprovementJson(), List.class),
                    objectMapper.readValue(review.getSuggestedRefactorsJson(), List.class)
            );

            return ResponseEntity.ok(
                    new ReviewDetailDTO(review.getId(), review.getPrUrl(), aiReview)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error parsing review JSON: " + e.getMessage());
        }
    }
}
