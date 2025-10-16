package com.example.ai_code_review_tool.github.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubPrDetailsDTO {
    private String prUrl;
    private String title;
    private String description;
    private List<GithubFileDTO> changedFiles;
}
