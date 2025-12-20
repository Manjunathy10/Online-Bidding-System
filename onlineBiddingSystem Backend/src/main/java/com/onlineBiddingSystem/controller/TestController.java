package com.onlineBiddingSystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBiddingSystem.entity.User;
import com.onlineBiddingSystem.repository.UserRepository;

@RestController
public class TestController {
	   private final UserRepository userRepository;

	    public TestController(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }

	    @GetMapping("/test/users")
	    public List<User> getUsers() {
	        return userRepository.findAll();
	    }
}
