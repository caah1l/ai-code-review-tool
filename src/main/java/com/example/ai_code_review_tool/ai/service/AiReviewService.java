package com.example.ai_code_review_tool.ai.service;

import com.example.ai_code_review_tool.ai.dto.AiReviewDTO;
import com.example.ai_code_review_tool.ai.prompt.PromptTemplates;
import com.example.ai_code_review_tool.github.dto.GithubPrDetailsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
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

        // Explicitly specify the Groq model here
        String aiResponse = chatClient.prompt()
                .options(ChatOptions.builder()
                        .model("llama-3.1-8b-instant")
                        .build())
                .user(prompt)
                .call()
                .content();

//        try {
//            return objectMapper.readValue(aiResponse, AiReviewDTO.class);
//        } catch (Exception e) {
//            return new AiReviewDTO(
//                    "Error parsing AI response. Raw output:\n" + aiResponse,
//                    List.of(),
//                    List.of()
//            );
//        }

        try {
            // Try parsing directly
            return objectMapper.readValue(aiResponse, AiReviewDTO.class);
        } catch (Exception e1) {
            try {
                // Handle double-encoded JSON (string inside string)
                String cleaned = aiResponse
                        .replaceAll("(?s)^.*?(\\{.*\\}).*$", "$1")  // extract JSON
                        .replace("\\n", "")
                        .replace("\\\"", "\"");
                return objectMapper.readValue(cleaned, AiReviewDTO.class);
            } catch (Exception e2) {
                // If still fails, return raw output
                return new AiReviewDTO(
                        "Error parsing AI response. Raw output:\n" + aiResponse,
                        List.of(),
                        List.of()
                );
            }
        }
    }
}
