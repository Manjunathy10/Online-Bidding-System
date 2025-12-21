package com.substring.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.substring.auth.dtos.UserDto;
import com.substring.auth.service.UserService;
import com.substring.auth.service.impl.UserServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

	private final UserServiceImpl userServiceImpl;
	private final UserService userService;

	// create an user api
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
	}

	// get all user
	@GetMapping
	public ResponseEntity<Iterable<UserDto>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	// get user by mail
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByMail(@PathVariable String email) {
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}

	// delete user
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
	}

	// update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updatUser(@PathVariable String userId, @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.updateUser(userDto, userId));
	}

	// get user ById
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}

}
