package com.example.ai_code_review_tool.github.service;

import com.example.ai_code_review_tool.github.dto.GithubFileDTO;
import com.example.ai_code_review_tool.github.dto.GithubPrDetailsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GithubService {
//    public GithubPrDetailsDTO analyzePullRequest(String prUrl) {
//        return new GithubPrDetailsDTO(
//                prUrl,
//                "Sample PR: Improve logging and exception handling",
//                "This PR refactors logging setup, adds custom exception handler, and updates service layer.",
//                List.of(
//                        new GithubFileDTO("src/main/java/com/example/ai_code_review_tool/config/SecurityConfig.java", "modified"),
//                        new GithubFileDTO("src/main/java/com/example/ai_code_review_tool/auth/service/AuthService.java", "modified")
//                )
//        );
//    }

    @Value("${github.api.url}")
    private String githubApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public GithubPrDetailsDTO analyzePullRequest(String prUrl) {

        try{

        String[] url = prUrl.replace("https://github.com/", "").split("/");
        if (url.length < 4) {
            throw new IllegalArgumentException("Invalid GitHub PR URL format");
        }
        String owner = url[0];
        String repo = url[1];
        String prId = url[3];

        String prDetailsUrl = String.format("%s/repos/%s/%s/pulls/%s", githubApiUrl, owner, repo, prId);
        String prFilesUrl = prDetailsUrl + "/files";

       // ResponseEntity<List> prResponse = restTemplate.exchange(prDetailsUrl, HttpMethod.GET, null, List.class);
        ResponseEntity<Map> prResponse = restTemplate.exchange(prDetailsUrl, HttpMethod.GET, null, Map.class);
        Map<String, Object> prData = prResponse.getBody();


        ResponseEntity<List> filesResponse = restTemplate.exchange(prFilesUrl, HttpMethod.GET, null, List.class);
        List<Map<String, Object>> filesData = filesResponse.getBody();

        List<GithubFileDTO> files = filesData.stream()
                .map(f -> new GithubFileDTO(
                        (String) f.get("filename"),
                        (String) f.get("status")
                       // (String) f.get("patch")

                ))
                .toList();

        return new GithubPrDetailsDTO(
                prUrl,
                (String) prData.get("title"),
                (String) prData.get("body"),
                files
        );

    } catch (Exception e) {
        throw new RuntimeException("Error fetching PR details: " + e.getMessage(), e);
    }

    }

}
