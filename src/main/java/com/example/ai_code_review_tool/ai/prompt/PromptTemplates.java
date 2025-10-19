package com.example.ai_code_review_tool.ai.prompt;

import com.example.ai_code_review_tool.github.dto.GithubPrDetailsDTO;

import java.util.stream.Collectors;

public class PromptTemplates {
    public static String buildPrompt(GithubPrDetailsDTO prDetails) {
        String filesData = prDetails.getChangedFiles().stream()
                .map(file -> String.format("""
                        File: %s
                        Status: %s
                        Patch:
                        %s
                        """,
                        file.getFilename(),
                        file.getStatus(),
                        file.getPatch() != null ? file.getPatch() : "[no diff available]")
                )
                .collect(Collectors.joining("\n\n"));

        return """
                You are an expert software reviewer.
                Analyze this GitHub Pull Request and return a JSON response matching this structure:

                {
                  "summary": "string",
                  "scopeOfImprovement": [
                    {
                      "file": "string",
                      "issues": [
                        { "title": "string", "description": "string", "severity": "LOW|MEDIUM|HIGH" }
                      ]
                    }
                  ],
                  "suggestedRefactors": [
                    {
                      "file": "string",
                      "refactorTitle": "string",
                      "explanation": "string",
                      "codeSnippet": "string"
                    }
                  ]
                }

                Be detailed and realistic in your feedback.
                Avoid markdown or comments, just valid JSON.

                Pull Request Info:
                - Title: %s
                - Description: %s
                - PR URL: %s

                Changed Files:
                %s
                """.formatted(
                prDetails.getTitle(),
                prDetails.getDescription() != null ? prDetails.getDescription() : "(no description provided)",
                prDetails.getPrUrl(),
                filesData
        );
    }
}

