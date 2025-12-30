package com.substring.auth.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.substring.auth.dtos.PersonalDetailsRequestDto;
import com.substring.auth.dtos.PersonalDetailsResponseDto;
import com.substring.auth.service.PersonalDetailsService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/profile")
public class PersonalDetailsController {

	private final PersonalDetailsService personalDetailsService;

	public PersonalDetailsController(PersonalDetailsService personalDetailsService) {
		super();
		this.personalDetailsService = personalDetailsService;
	}

	// create and update a profile
	 @PutMapping("/update")
	    public ResponseEntity<String> saveOrUpdate(
	            @RequestBody PersonalDetailsRequestDto request,
	            Authentication authentication) {

	        String email = authentication.getName(); // JWT se

	        personalDetailsService.saveOrUpdate(email, request);

	        return ResponseEntity.ok("Personal details saved successfully");
	    }

	// GET PROFILE
	@GetMapping
    public ResponseEntity<PersonalDetailsResponseDto> getMyDetails(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                personalDetailsService.getMyDetails(email)
        );
	
	
	}

}
