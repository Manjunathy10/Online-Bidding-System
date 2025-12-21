package com.substring.auth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.substring.auth.dtos.UserDto;
import com.substring.auth.service.AuthService;
import com.substring.auth.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerUser(UserDto userDto) {

		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		UserDto userDto1 = userService.createUser(userDto);
		return userDto1;
	}

}
