package com.substring.auth.dtos;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDetailsResponseDto {

	private String firstName;
	private String lastName;
	private String phone;

	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;

	private Instant createdAt;
	private Instant updatedAt;

}
