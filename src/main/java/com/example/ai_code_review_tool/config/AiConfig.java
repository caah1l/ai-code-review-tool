package com.example.ai_code_review_tool.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AiConfig {

        @Value("${spring.ai.openai.api-key}")
        private String apiKey;

        @Value("${spring.ai.openai.base-url}")
        private String baseUrl;


    @Bean
    public ChatClient chatClient() {
        // Create OpenAI-compatible API pointing to Groq
        OpenAiApi api = new OpenAiApi(baseUrl, apiKey);

        // Build the chat model wrapper
        OpenAiChatModel chatModel = new OpenAiChatModel(api);

        // ✅ This builder works on M6
        return ChatClient.builder(chatModel).build();
    }
        }


