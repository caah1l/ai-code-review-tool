package com.example.ai_code_review_tool.review.dto;


import com.example.ai_code_review_tool.ai.dto.AiReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailDTO
{
    private Long id;
    private String prUrl;
    private AiReviewDTO aiReview;
}
