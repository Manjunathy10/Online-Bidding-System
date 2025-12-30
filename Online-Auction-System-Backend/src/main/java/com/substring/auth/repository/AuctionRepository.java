package com.substring.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.substring.auth.entity.Auction;
import com.substring.auth.enums.AuctionStatus;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

	// fetch auction by sub-category
	List<Auction> findBySubCategoryId(Long subCategoryId);

	// fetch auction by category (via subCategory)
	List<Auction> findBySubCategoryCategoryId(Long categoryId);

	// seller ke auctions mens seller how many auction belong to mail
	List<Auction> findByOwnerEmail(String email);

	// live auctions ya status ke hisab sai auction like created active closed
	List<Auction> findByStatus(AuctionStatus status);

}
