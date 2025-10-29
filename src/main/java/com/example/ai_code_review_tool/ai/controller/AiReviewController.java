package com.example.ai_code_review_tool.ai.controller;

import com.example.ai_code_review_tool.ai.dto.AiReviewDTO;
import com.example.ai_code_review_tool.ai.service.AiReviewService;
import com.example.ai_code_review_tool.github.dto.GithubPrDetailsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
public class AiReviewController {

    private final AiReviewService aiReviewService;

    public AiReviewController(AiReviewService aiReviewService) {
        this.aiReviewService = aiReviewService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<AiReviewDTO> analyzePullRequest(@RequestBody GithubPrDetailsDTO prDetails) {
        AiReviewDTO response = aiReviewService.analyzePullRequest(prDetails);
        return ResponseEntity.ok(response);
    }
}
