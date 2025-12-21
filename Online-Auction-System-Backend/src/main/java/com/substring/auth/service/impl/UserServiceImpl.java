package com.substring.auth.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.substring.auth.dtos.UserDto;
import com.substring.auth.entity.Provider;
import com.substring.auth.entity.User;
import com.substring.auth.exception.ResourceNotFoundException;
import com.substring.auth.helper.UserHelper;
import com.substring.auth.repository.UserRepository;
import com.substring.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {

		if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
			throw new IllegalArgumentException("Email is Required");
		}
		if (userRepository.existsByEmail(userDto.getEmail())) {
			throw new IllegalArgumentException("User with given email already exit..");
		}

		// if you have any extra check put here

		User user = modelMapper.map(userDto, User.class);
		// this should be define a default provider automatically set karega
		userDto.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);

		// assign a role to user here for authorization
		User savedUSer = userRepository.save(user);

		return modelMapper.map(savedUSer, UserDto.class);
	}

	@Override
	public UserDto getUserByEmail(String emil) {
		User user = userRepository.findByEmail(emil)
				.orElseThrow(() -> new ResourceNotFoundException("user not found with this email id ..."));
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
	
		UUID uuid=UserHelper.parseUUID(userId);
		User exitingUser=userRepository.findById(uuid).orElseThrow(()-> new ResourceNotFoundException("user not found with given id"));
		
			if(userDto.getName()!=null) exitingUser.setName(userDto.getName());
			if(userDto.getImage()!=null) exitingUser.setImage(userDto.getImage());
			if(userDto.getProvider()!=null) exitingUser.setProvider(userDto.getProvider());
			// cghange the password updataion logic 
			if(userDto.getPassword()!=null) exitingUser.setPassword(userDto.getPassword());
			exitingUser.setEnable(userDto.isEnable());
			
			userRepository.save(exitingUser);
			
		return modelMapper.map(exitingUser, UserDto.class);
	}

	@Override
	public void deleteUser(String UserId) {
		UUID uId = UserHelper.parseUUID(UserId);
		User user = userRepository.findById(uId)
				.orElseThrow(() -> new ResourceNotFoundException("User will e not found..."));
		userRepository.delete(user);
	}

	@Override
	public UserDto getUserById(String UserId) {
		User user = userRepository.findById(UserHelper.parseUUID(UserId))
				.orElseThrow(() -> new ResourceNotFoundException("User will e not found..."));
		return modelMapper.map(user, UserDto.class);
	}

	// get all user
	@Override
	public Iterable<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).toList();

	}

}
