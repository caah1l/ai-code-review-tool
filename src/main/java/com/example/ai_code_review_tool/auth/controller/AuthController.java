package com.example.ai_code_review_tool.auth.controller;

import com.example.ai_code_review_tool.auth.dto.AuthResponseDTO;
import com.example.ai_code_review_tool.auth.dto.LoginRequestDTO;
import com.example.ai_code_review_tool.auth.dto.RegisterRequestDTO;
import com.example.ai_code_review_tool.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO req) {
        authService.register(req);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {
        AuthResponseDTO resp = authService.login(req);
        return ResponseEntity.ok(resp);
    }
}
