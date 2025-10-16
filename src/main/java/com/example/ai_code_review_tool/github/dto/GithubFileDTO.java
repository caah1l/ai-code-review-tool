package com.example.ai_code_review_tool.github.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubFileDTO {
    private String filename;
    private String status;
}
