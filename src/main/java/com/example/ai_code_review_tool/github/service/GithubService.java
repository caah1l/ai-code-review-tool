package com.example.ai_code_review_tool.github.service;

import com.example.ai_code_review_tool.github.dto.GithubFileDTO;
import com.example.ai_code_review_tool.github.dto.GithubPrDetailsDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class GithubService {
    public GithubPrDetailsDTO analyzePullRequest(String prUrl) {
        return new GithubPrDetailsDTO(
                prUrl,
                "Sample PR: Improve logging and exception handling",
                "This PR refactors logging setup, adds custom exception handler, and updates service layer.",
                List.of(
                        new GithubFileDTO("src/main/java/com/example/ai_code_review_tool/config/SecurityConfig.java", "modified"),
                        new GithubFileDTO("src/main/java/com/example/ai_code_review_tool/auth/service/AuthService.java", "modified")
                )
        );
    }
}
