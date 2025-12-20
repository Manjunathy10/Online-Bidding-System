package com.onlineBiddingSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
	// auth fields
	private String email;
	private String password;

	// personal details
	private String firstName;
	private String middleName;
	private String lastName;
	private String phone;
	private String city;
	private String state;
	private String country;
	
	private String addressLine1;
	private String addressLine2;
}
