package com.onlineBiddingSystem.entity;

import java.time.Instant;

import com.onlineBiddingSystem.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")

// lombok 
@Getter
@Setter
@AllArgsConstructor
@Builder

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
// this field for authorization
	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role = Role.USER;


// account status 
	@Column(nullable = false)
	private boolean enabled = true;

	@Column(nullable = false)
	private boolean accountLocked = false;

	@Column(nullable = false)
	private boolean emailVerified = false;



	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@Column(nullable = false)
	private Instant updatedAt;

	private Instant lastLoginAt;



	private Instant passwordChangedAt;



	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private PersonalDetails personalDetails;

	/* ================= HIBERNATE LIFECYCLE ================= */

	@PrePersist
	protected void onCreate() {
		Instant now = Instant.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = Instant.now();
	}

	

	public void changePassword(String encodedPassword) {
		this.password = encodedPassword;
		this.passwordChangedAt = Instant.now();
	}

}
