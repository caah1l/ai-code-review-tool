package com.example.ai_code_review_tool.review.model;

import com.example.ai_code_review_tool.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prUrl;
    private String summary;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String scopeOfImprovementJson;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String suggestedRefactorsJson;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
