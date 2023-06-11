package com.uit.flowerstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uit.flowerstore.domain.WishlistItem;
import com.uit.flowerstore.repository.WishlistItemRepository;

import com.uit.flowerstore.services.WishlistService;

@RestController
@RequestMapping("/api")
public class WishlistController {
	private final WishlistService wishlistService;
    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }
    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @GetMapping("/wishlist/{userId}")
    public List<WishlistItem> getWishlistByUserId(@PathVariable Long userId) {
        return wishlistItemRepository.findByUserId(userId);
    }
    
    @DeleteMapping("/wishlist/{userId}")
    public ResponseEntity<?> deleteWishlist(@PathVariable Long userId) {
        wishlistService.deleteWishlistByUserId(userId);
        return ResponseEntity.ok().build();
    }
}
