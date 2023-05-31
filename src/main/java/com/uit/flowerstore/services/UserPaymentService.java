package com.uit.flowerstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uit.flowerstore.repository.UserPaymentRepository;

@Service
public class UserPaymentService {
    private final UserPaymentRepository userPaymentRepository;

    @Autowired
    public UserPaymentService(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    // Add any additional methods you need for UserPayment service
}