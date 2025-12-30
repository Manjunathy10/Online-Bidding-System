package com.substring.auth.service;

import java.util.List;

public interface AuctionService {

	// 🔹 CREATE auction (Seller)
	AuctionResponseDto createAuction(String email, CreateAuctionRequestDto request);

	// 🔹 UPDATE auction (Only owner, before time limit)
	AuctionResponseDto updateAuction(Long auctionId, String email, UpdateAuctionRequestDto request);

	// 🔹 DELETE auction (Only owner, before start)
	void deleteAuction(Long auctionId, String email);

	// 🔹 FETCH auctions by Category
	List<AuctionResponseDto> getByCategory(Long categoryId);

	// 🔹 FETCH auctions by SubCategory
	List<AuctionResponseDto> getBySubCategory(Long subCategoryId);
}
