package com.uit.flowerstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uit.flowerstore.domain.UserPayment;
import com.uit.flowerstore.repository.UserPaymentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserPaymentService {
    private final UserPaymentRepository userPaymentRepository;

    public UserPaymentService(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }
    
    public UserPayment createUserPayment(UserPayment userPayment) {
    	return userPaymentRepository.save(userPayment);
    }

    

    public UserPayment getUserPaymentById(Long id) {
        return userPaymentRepository.findById(id).orElse(null);
    }

    public List<UserPayment> getAllUserPayments() {
        return userPaymentRepository.findAll();
    }

    public void deleteUserPayment(Long id) {
        userPaymentRepository.deleteById(id);
    }
    public UserPayment updateUserPayment(Long id, UserPayment updatedUserPayment) {
        UserPayment existingUserPayment = userPaymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserPayment not found with id: " + id));

        // Update the properties of existingUserPayment with the values from updatedUserPayment
        existingUserPayment.setCardName(updatedUserPayment.getCardName());
        existingUserPayment.setCardNumber(updatedUserPayment.getCardNumber());
        existingUserPayment.setCvc(updatedUserPayment.getCvc());
        existingUserPayment.setDefaultPayment(updatedUserPayment.getDefaultPayment());
        existingUserPayment.setExpiryMonth(updatedUserPayment.getExpiryMonth());
        existingUserPayment.setExpiryYear(updatedUserPayment.getExpiryYear());
        existingUserPayment.setHolderName(updatedUserPayment.getHolderName());
        existingUserPayment.setType(updatedUserPayment.getType());

        return userPaymentRepository.save(existingUserPayment);
    }
}
