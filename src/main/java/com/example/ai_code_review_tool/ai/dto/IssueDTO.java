package com.example.ai_code_review_tool.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private String title;
    private String description;
    private String severity;
}
