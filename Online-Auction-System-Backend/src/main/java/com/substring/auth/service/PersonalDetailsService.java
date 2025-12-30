package com.substring.auth.service;

import com.substring.auth.dtos.PersonalDetailsRequestDto;
import com.substring.auth.dtos.PersonalDetailsResponseDto;

public interface PersonalDetailsService {

	void saveOrUpdate(String email, PersonalDetailsRequestDto request);

	PersonalDetailsResponseDto getMyDetails(String email);

}
