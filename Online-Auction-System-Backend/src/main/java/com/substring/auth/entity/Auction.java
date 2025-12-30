package com.substring.auth.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.substring.auth.enums.AuctionStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// -------- Product Info --------
	@Column(nullable = false)
	private String title;

	@Column(length = 1000)
	private String description;

	@Column(nullable = false)
	private BigDecimal basePrice;

	// -------- Auction Time --------
	private Instant startTime;
	private Instant endTime;

	@Enumerated(EnumType.STRING)
	private AuctionStatus status;

	// -------- Seller --------
	@ManyToOne(fetch = FetchType.LAZY)   // one user have many auction 
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	// -------- Category --------
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_category_id", nullable = false)
	private SubCategory subCategory;

	// MULTIPLE IMAGES can fetch and store in list 
	@OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AuctionImage> images;

	// -------- Audit --------
	private Instant createdAt;
	private Instant updatedAt;

	@PrePersist
	protected void onCreate() {
		Instant now = Instant.now();
		this.createdAt = now;
		this.updatedAt = now;
		this.status = AuctionStatus.CREATED;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = Instant.now();
	}
}
