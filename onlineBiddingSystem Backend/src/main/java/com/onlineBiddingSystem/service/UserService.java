package com.onlineBiddingSystem.service;

import org.springframework.stereotype.Service;

import com.onlineBiddingSystem.dto.RegisterRequest;
import com.onlineBiddingSystem.entity.PersonalDetails;
import com.onlineBiddingSystem.entity.User;
import com.onlineBiddingSystem.enums.Role;
import com.onlineBiddingSystem.exception.EmailAlreadyExistsException;
import com.onlineBiddingSystem.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User register(RegisterRequest request) {

		// step 1 : check email is already exit
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException("Email already registered");
		}

		// step 2 : cerate a user

		User user = User.builder().email(request.getEmail()).password(passwordEncoder.) // plain password (for now)
				.role(Role.USER).enabled(true).emailVerified(false).accountLocked(false).build();

		// step 3: create personal details
		PersonalDetails details = PersonalDetails.builder().firstName(request.getFirstName())
				.middleName(request.getMiddleName()).lastName(request.getLastName()).phone(request.getPhone())
				.addressLine1(request.getAddressLine1()).addressLine2(request.getAddressLine2())
				.city(request.getCity()).state(request.getState()).country(request.getCountry()).build();

		// step :4 map relation
		details.setUser(user);
		user.setPersonalDetails(details);

		// step :5 save (cascade will save personal_details also)
		return userRepository.save(user);
	}
}
