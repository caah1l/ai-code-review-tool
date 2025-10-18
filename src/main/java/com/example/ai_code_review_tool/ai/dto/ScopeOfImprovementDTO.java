package com.example.ai_code_review_tool.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScopeOfImprovementDTO {
    private String file;
    private List<IssueDTO> issues;
}
