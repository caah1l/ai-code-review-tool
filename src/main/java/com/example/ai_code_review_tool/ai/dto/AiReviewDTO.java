package com.example.ai_code_review_tool.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiReviewDTO {
    private String summary;
    private List<ScopeOfImprovementDTO> scopeOfImprovement;
    private List<SuggestedRefactorDTO> suggestedRefactors;
}
