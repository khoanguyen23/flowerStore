package com.uit.flowerstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uit.flowerstore.domain.UserShipping;
import com.uit.flowerstore.services.UserShippingService;
import org.springframework.http.HttpHeaders;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserShippingController {
    private final UserShippingService userShippingService;

    @Autowired
    public UserShippingController(UserShippingService userShippingService) {
        this.userShippingService = userShippingService;
    }

    @PostMapping("/user-shipping")
    public ResponseEntity<UserShipping> createUserShipping(@RequestBody UserShipping userShipping) {
        UserShipping createdUserShipping = userShippingService.createUserShipping(userShipping);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserShipping);
    }

    @GetMapping("/user-shipping/{id}")
    public ResponseEntity<UserShipping> getUserShipping(@PathVariable Long id) {
        UserShipping userShipping = userShippingService.getUserShippingById(id);
        return ResponseEntity.ok(userShipping);
    }

    @PutMapping("/user-shipping/{id}")
    public ResponseEntity<UserShipping> updateUserShipping(@PathVariable Long id, @RequestBody UserShipping userShipping) {
        UserShipping updatedUserShipping = userShippingService.updateUserShipping(id, userShipping);
        return ResponseEntity.ok(updatedUserShipping);
    }

    @DeleteMapping("user-shipping/{id}")
    public ResponseEntity<Void> deleteUserShipping(@PathVariable Long id) {
        userShippingService.deleteUserShipping(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("XoaThanhCong", "Xóa thành công");
        return ResponseEntity.noContent().headers(headers).build();
    }
}

