package com.substring.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.substring.auth.entity.PersonalDetails;

public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Long> {

}
