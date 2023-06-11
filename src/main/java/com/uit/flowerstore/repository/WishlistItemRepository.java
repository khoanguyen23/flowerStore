package com.uit.flowerstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uit.flowerstore.domain.WishlistItem;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
	
	List<WishlistItem> findByUserId(Long userId);
	void deleteByUserId(Long userId);

}
