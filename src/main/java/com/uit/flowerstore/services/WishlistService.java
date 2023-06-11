package com.uit.flowerstore.services;

import org.springframework.stereotype.Service;

import com.uit.flowerstore.repository.WishlistItemRepository;

@Service
public class WishlistService {
	private final WishlistItemRepository wishlistItemRepository;

    public WishlistService(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }
    public void deleteWishlistByUserId(Long userId) {
        wishlistItemRepository.deleteByUserId(userId);
    }

}
