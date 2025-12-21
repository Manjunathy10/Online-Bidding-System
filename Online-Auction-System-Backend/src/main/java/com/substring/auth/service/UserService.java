package com.substring.auth.service;

import com.substring.auth.dtos.UserDto;

public interface UserService {

	// Create user
	UserDto createUser(UserDto userDto);

	// get user by email
	UserDto getUserByEmail(String emil);

	// update user
	UserDto updateUser(UserDto userDto, String userId);

	// delete user
	void deleteUser(String UserId);

	// get user by id
	UserDto getUserById(String UserId);

	// get all user
	Iterable<UserDto> getAllUsers();
}
