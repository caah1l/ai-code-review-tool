package com.example.ai_code_review_tool.ai.service;

import com.example.ai_code_review_tool.ai.dto.AiReviewDTO;
import com.example.ai_code_review_tool.ai.prompt.PromptTemplates;
import com.example.ai_code_review_tool.auth.repository.UserRepository;
import com.example.ai_code_review_tool.github.dto.GithubPrDetailsDTO;
import com.example.ai_code_review_tool.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiReviewService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    public AiReviewService(ChatClient chatClient,ReviewService reviewService, UserRepository userRepository) {
        this.chatClient = chatClient;
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }

    public AiReviewDTO analyzePullRequest(GithubPrDetailsDTO prDetails) {
        String prompt = PromptTemplates.buildPrompt(prDetails);


        String aiResponse = chatClient.prompt()
                .options(ChatOptions.builder()
                        .model("llama-3.1-8b-instant")
                        .build())
                .user(prompt)
                .call()
                .content();

          AiReviewDTO aiReview;

        try {

           // return objectMapper.readValue(aiResponse, AiReviewDTO.class);
            aiReview = objectMapper.readValue(aiResponse, AiReviewDTO.class);
        } catch (Exception e1) {
            try {

                String cleaned = aiResponse
                        .replaceAll("(?s)^.*?(\\{.*\\}).*$", "$1")  // extract JSON
                        .replace("\\n", "")
                        .replace("\\\"", "\"");
                return objectMapper.readValue(cleaned, AiReviewDTO.class);
            } catch (Exception e2) {

                return new AiReviewDTO(
                        "Error parsing AI response. Raw output:\n" + aiResponse,
                        List.of(),
                        List.of()
                );
            }
        }

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userRepository.findByUsername(username).ifPresent(user ->
                    reviewService.saveReview(aiReview, prDetails.getPrUrl(), user)
            );
        } catch (Exception e) {
            System.err.println("Failed to auto-save review: " + e.getMessage());
        }

        return aiReview;

    }

}
