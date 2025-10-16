package com.example.ai_code_review_tool.github.controller;

import com.example.ai_code_review_tool.github.dto.GithubPrDetailsDTO;
import com.example.ai_code_review_tool.github.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/github")

public class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<GithubPrDetailsDTO> analyzePR(@RequestBody GithubPrDetailsDTO request) {
        GithubPrDetailsDTO response = githubService.analyzePullRequest(request.getPrUrl());
        return ResponseEntity.ok(response);
    }
}
