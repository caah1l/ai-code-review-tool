package com.example.ai_code_review_tool.auth.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class AuthResponseDTO {
    private String token;
    private String tokenType = "Bearer";
}
