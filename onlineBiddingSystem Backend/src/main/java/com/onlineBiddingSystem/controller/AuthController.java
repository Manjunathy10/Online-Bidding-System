package com.onlineBiddingSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.onlineBiddingSystem.dto.RegisterRequest;
import com.onlineBiddingSystem.entity.User;
import com.onlineBiddingSystem.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    
    // for creating and registring a user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        User user = userService.register(request);

        return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
    }
}
