package com.uit.flowerstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.uit.flowerstore.domain.UserPayment;
import com.uit.flowerstore.domain.UserShipping;
import com.uit.flowerstore.security.services.UserDetailsImpl;
import com.uit.flowerstore.services.UserPaymentService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserPaymentController {
    private final UserPaymentService userPaymentService;

    @Autowired
    public UserPaymentController(UserPaymentService userPaymentService) {
        this.userPaymentService = userPaymentService;
    }
    @PostMapping("/user-payment")
    public ResponseEntity<UserPayment> createUserPayment(@RequestBody UserPayment userPayment, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((Authentication) principal).getPrincipal();
        UserPayment createdUserPayment = userPaymentService.createUserPayment(userPayment, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserPayment);
    }
    
    @GetMapping("/user-payment")
    public ResponseEntity<List<UserPayment>> getUserPaymentList(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<UserPayment> userPaymentList = userPaymentService.getUserPaymentList(userDetails);
        if (!userPaymentList.isEmpty()) {
            return ResponseEntity.ok(userPaymentList);
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/user-payment/default/{paymentId}")
    public ResponseEntity<?> updateDefaultUserPayment(@PathVariable Long paymentId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserPayment defaultUserPayment = userPaymentService.updateDefaultUserPayment(userDetails, paymentId);
        if (defaultUserPayment != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    
    
    @DeleteMapping("user-payment/{id}")
    public ResponseEntity<Void> deleteUserPayment(@PathVariable Long id) {
        userPaymentService.deleteUserPayment(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("XoaThanhCong", "Xóa thành công");
        return ResponseEntity.noContent().headers(headers).build();
    }
    
    @GetMapping("/user-payment/{id}")
    public ResponseEntity<UserPayment> getUserPayment(@PathVariable Long id) {
        UserPayment userPayment = userPaymentService.getUserPaymentById(id);
        return ResponseEntity.ok(userPayment);
    }

    @PutMapping("/user-payment/{id}")
    public ResponseEntity<UserPayment> updateUserPayment(@PathVariable Long id, @RequestBody UserPayment userPayment) {
        UserPayment updatedUserPayment = userPaymentService.updateUserPayment(id, userPayment);
        return ResponseEntity.ok(updatedUserPayment);
    }

    
}
