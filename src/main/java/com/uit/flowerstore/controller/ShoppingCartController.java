package com.uit.flowerstore.controller;

import com.uit.flowerstore.domain.ShoppingCart;
import com.uit.flowerstore.services.ShoppingCartService;
import com.uit.flowerstore.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getUserShoppingCarts(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<ShoppingCart> shoppingCarts = shoppingCartService.getUserShoppingCarts(userDetails);
        return ResponseEntity.ok(shoppingCarts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable("id") Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartById(id, userDetails);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCart shoppingCart, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCart, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShoppingCart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable("id") Long id, @RequestBody ShoppingCart updatedShoppingCart, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ShoppingCart shoppingCart = shoppingCartService.updateShoppingCart(id, updatedShoppingCart, userDetails);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable("id") Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        shoppingCartService.deleteShoppingCart(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
