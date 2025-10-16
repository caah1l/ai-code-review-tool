package com.example.ai_code_review_tool.auth.service;

import com.example.ai_code_review_tool.auth.dto.AuthResponseDTO;
import com.example.ai_code_review_tool.auth.dto.LoginRequestDTO;
import com.example.ai_code_review_tool.auth.dto.RegisterRequestDTO;
import com.example.ai_code_review_tool.auth.model.User;
import com.example.ai_code_review_tool.auth.repository.UserRepository;
import com.example.ai_code_review_tool.config.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequestDTO req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    public AuthResponseDTO login(LoginRequestDTO req) {
        User user = userRepository.findByUsername(req.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(req.getUsernameOrEmail()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid username/email or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username/email or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponseDTO(token,"Bearer");
    }
}
