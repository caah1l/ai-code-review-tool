package com.example.ai_code_review_tool.auth.repository;

import com.example.ai_code_review_tool.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

 public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);
        Optional<User> findByEmail(String email);
        boolean existsByUsername(String username);
        boolean existsByEmail(String email);
    }

