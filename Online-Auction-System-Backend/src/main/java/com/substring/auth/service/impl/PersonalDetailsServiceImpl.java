package com.substring.auth.service.impl;

import org.springframework.stereotype.Service;

import com.substring.auth.dtos.PersonalDetailsRequestDto;
import com.substring.auth.dtos.PersonalDetailsResponseDto;
import com.substring.auth.entity.PersonalDetails;
import com.substring.auth.entity.User;
import com.substring.auth.repository.PersonalDetailsRepository;
import com.substring.auth.repository.UserRepository;
import com.substring.auth.service.PersonalDetailsService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

	private final UserRepository userRepository;
	private final PersonalDetailsRepository personalDetailsRepository;

	public PersonalDetailsServiceImpl(UserRepository userRepository,
			PersonalDetailsRepository personalDetailsRepository) {
		this.userRepository = userRepository;
		this.personalDetailsRepository = personalDetailsRepository;
	}

	@Override
	public void saveOrUpdate(String email, PersonalDetailsRequestDto request) {

		// 1️⃣ Logged-in user lao
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		// 2️⃣ Owning side handle karo
		PersonalDetails pd = user.getPersonalDetails();

		if (pd == null) {
			pd = new PersonalDetails();
			pd.setUser(user); // 🔥 OWNING SIDE
			user.setPersonalDetails(pd); // bidirectional sync
		}

		// 3️⃣ Fields set karo
		pd.setFirstName(request.getFirstName());
		pd.setLastName(request.getLastName());
		pd.setPhone(request.getPhone());
		pd.setAddressLine1(request.getAddressLine1());
		pd.setAddressLine2(request.getAddressLine2());
		pd.setCity(request.getCity());
		pd.setState(request.getState());
		pd.setCountry(request.getCountry());

		// 4️⃣ 🔥 SAVE OWNING SIDE
		personalDetailsRepository.save(pd);
	}

	@Override
	public PersonalDetailsResponseDto getMyDetails(String email) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		PersonalDetails pd = user.getPersonalDetails();

		if (pd == null) {
			return null;
		}

		return PersonalDetailsResponseDto.builder().firstName(pd.getFirstName()).lastName(pd.getLastName())
				.phone(pd.getPhone()).addressLine1(pd.getAddressLine1()).addressLine2(pd.getAddressLine2())
				.city(pd.getCity()).state(pd.getState()).country(pd.getCountry()).createdAt(pd.getCreatedAt())
				.updatedAt(pd.getUpdatedAt()).build();
	}
}
