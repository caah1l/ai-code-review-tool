package com.example.ai_code_review_tool.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestedRefactorDTO {
    private String file;
    private String refactorTitle;
    private String explanation;
    private String codeSnippet;
}
