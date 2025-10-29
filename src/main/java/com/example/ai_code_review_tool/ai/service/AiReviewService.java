package com.example.ai_code_review_tool.ai.service;

import com.example.ai_code_review_tool.ai.dto.AiReviewDTO;
import com.example.ai_code_review_tool.ai.prompt.PromptTemplates;
import com.example.ai_code_review_tool.github.dto.GithubPrDetailsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiReviewService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiReviewService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public AiReviewDTO analyzePullRequest(GithubPrDetailsDTO prDetails) {
        String prompt = PromptTemplates.buildPrompt(prDetails);

        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        try {
            return objectMapper.readValue(aiResponse, AiReviewDTO.class);
        } catch (Exception e) {

            return new AiReviewDTO(
                    "Error parsing AI response. Raw output:\n" + aiResponse,
                    List.of(),
                    List.of()
            );
        }
    }
}
