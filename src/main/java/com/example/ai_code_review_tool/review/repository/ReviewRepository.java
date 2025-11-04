package com.example.ai_code_review_tool.review.repository;

import com.example.ai_code_review_tool.auth.model.User;
import com.example.ai_code_review_tool.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByUser(User user);
}
