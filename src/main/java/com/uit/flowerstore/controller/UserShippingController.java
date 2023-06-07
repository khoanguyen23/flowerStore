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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserShippingController {
    private final UserShippingService userShippingService;
    @Autowired
    public UserShippingController(UserShippingService userShippingService) {
        this.userShippingService = userShippingService;
    }

    @PostMapping("/user-shipping")
    public ResponseEntity<UserShipping> createUserShipping(@RequestBody UserShipping userShipping, Principal principal) {
   	 UserDetailsImpl userDetails = (UserDetailsImpl) ((Authentication) principal).getPrincipal();
   	 UserShipping createdUserShipping = userShippingService.createUserShipping(userShipping, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserShipping);
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
    
    @GetMapping("/user-shipping")
    public ResponseEntity<List<UserShipping>> getUserShippingList(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<UserShipping> userShippingList = userShippingService.getUserShippingList(userDetails);
        if (!userShippingList.isEmpty()) {
            return ResponseEntity.ok(userShippingList);
        }
        return ResponseEntity.notFound().build();
    }
//    @GetMapping("/user-shipping/default")
//    public ResponseEntity<UserShipping> getDefaultUserShipping(Authentication authentication) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        UserShipping defaultUserShipping = userShippingService.getDefaultUserShipping(userDetails);
//        if (defaultUserShipping != null) {
//            return ResponseEntity.ok(defaultUserShipping);
//        }
//        return ResponseEntity.notFound().build();
//    }
//    
    @PutMapping("/user-shipping/default/{shippingId}")
    public ResponseEntity<?> updateDefaultUserShipping(@PathVariable Long shippingId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserShipping defaultUserShipping = userShippingService.updateDefaultUserShipping(userDetails, shippingId);
        if (defaultUserShipping != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    

}