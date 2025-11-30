package com.example.ai_code_review_tool.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewListDTO {
    private Long id;
    private String prUrl;
    private String summary;
    private LocalDateTime createdAt;
}