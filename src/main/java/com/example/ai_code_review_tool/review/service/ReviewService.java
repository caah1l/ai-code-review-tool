package com.example.ai_code_review_tool.review.service;

import com.example.ai_code_review_tool.ai.dto.AiReviewDTO;
import com.example.ai_code_review_tool.auth.model.User;
import com.example.ai_code_review_tool.review.model.Review;
import com.example.ai_code_review_tool.review.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void saveReview(AiReviewDTO aiReview, String prUrl, User user) {
        try {
            Review review = Review.builder()
                    .prUrl(prUrl)
                    .summary(aiReview.getSummary())
                    .scopeOfImprovementJson(objectMapper.writeValueAsString(aiReview.getScopeOfImprovement()))
                    .suggestedRefactorsJson(objectMapper.writeValueAsString(aiReview.getSuggestedRefactors()))
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();

            reviewRepository.save(review);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save review", e);
        }
    }

    public List<Review> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user);
    }
}
