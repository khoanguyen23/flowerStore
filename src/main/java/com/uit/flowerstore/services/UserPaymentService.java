package com.uit.flowerstore.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.UserPayment;
import com.uit.flowerstore.domain.UserShipping;
import com.uit.flowerstore.repository.UserPaymentRepository;
import com.uit.flowerstore.repository.UserRepository;

import com.uit.flowerstore.security.services.UserDetailsImpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserPaymentService {
	private final UserPaymentRepository userPaymentRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserPaymentService(UserPaymentRepository userPaymentRepository, UserRepository userRepository) {
        this.userPaymentRepository = userPaymentRepository;
        this.userRepository = userRepository;
    }
	    
    
    public UserPayment createUserPayment(UserPayment userPayment, UserDetailsImpl userDetails) {
    	User user = userRepository.findById(userDetails.getId()).orElse(null);
    	if(user != null) {
    		userPayment.setUser(user);
    		return userPaymentRepository.save(userPayment);
    	}
    	throw new EntityNotFoundException("User not found");
 
    }
    
    
    public List<UserPayment> getUserPaymentList(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            return user.getUserPayments();
        }
        return Collections.emptyList();
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
    
    public UserPayment updateDefaultUserPayment(UserDetailsImpl userDetails, Long paymentId) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null && user.getUserPayments() != null && !user.getUserPayments().isEmpty()) {
            UserPayment defaultUserPayment = null;
            for (UserPayment userPayment : user.getUserPayments()) {
                if (userPayment.getId().equals(paymentId)) {
                    userPayment.setDefaultPayment(true);
                    defaultUserPayment = userPayment;
                } else {
                    userPayment.setDefaultPayment(false);
                }
            }
            user.setUserPayments(user.getUserPayments()); //  cập nhật lại danh sách 
            userRepository.save(user);
            return defaultUserPayment;
        }
        return null;
    }
    
    
   

}
