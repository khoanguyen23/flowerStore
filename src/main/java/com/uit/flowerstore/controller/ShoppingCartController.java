package com.uit.flowerstore.controller;

import com.uit.flowerstore.domain.ShoppingCart;
import com.uit.flowerstore.services.ShoppingCartService;
import com.uit.flowerstore.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/shopping-cart")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ResponseEntity<ShoppingCart> getUserShoppingCarts(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ShoppingCart shoppingCarts = shoppingCartService.getUserShoppingCarts(userDetails);
        return ResponseEntity.ok(shoppingCarts);
    }
}
