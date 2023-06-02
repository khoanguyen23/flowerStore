package com.uit.flowerstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.uit.flowerstore.domain.UserShipping;
import com.uit.flowerstore.services.UserShippingService;
import com.uit.flowerstore.security.services.UserDetailsImpl;
import org.springframework.http.HttpHeaders;

import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserShippingController {
    private final UserShippingService userShippingService;

    @Autowired
    public UserShippingController(UserShippingService userShippingService) {
        this.userShippingService = userShippingService;
    }

//    @PostMapping("/user-shipping")
//    public ResponseEntity<UserShipping> createUserShipping(@RequestBody UserShipping userShipping) {
//        UserShipping createdUserShipping = userShippingService.createUserShipping(userShipping);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserShipping);
//    }
    
    @PostMapping("/user-shipping")
    public ResponseEntity<UserShipping> createUserShipping(@RequestBody UserShipping userShipping, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userShipping.getUser().setId(userDetails.getId()); 
        UserShipping createdUserShipping = userShippingService.createUserShipping(userShipping);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserShipping);
    }


    @GetMapping("/user-shipping/{id}")
    public ResponseEntity<UserShipping> getUserShipping(@PathVariable Long id) {
        UserShipping userShipping = userShippingService.getUserShippingById(id);
        return ResponseEntity.ok(userShipping);
    }
//    @GetMapping("/user-shipping")
//    public ResponseEntity<UserShipping> getUserShipping(Authentication authentication) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        UserShipping userShipping = userShippingService.getUserShipping(userDetails);
//        if (userShipping != null) {
//            return ResponseEntity.ok(userShipping);
//        }
//        return ResponseEntity.notFound().build();
//    }

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

