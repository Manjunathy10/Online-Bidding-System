package com.substring.auth.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionDto {

	private String title;
	private String description;
	private BigDecimal basePrice;

	private Instant startTime;
	private Instant endTime;

	private Long subCategoryId;

	// multiple image URLs
	private List<String> imageUrls;
}
