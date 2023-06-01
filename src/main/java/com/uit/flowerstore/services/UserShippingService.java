package com.uit.flowerstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.UserShipping;
import com.uit.flowerstore.repository.UserRepository;
import com.uit.flowerstore.repository.UserShippingRepository;
import com.uit.flowerstore.security.services.UserDetailsImpl;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserShippingService {
	
	 private final UserShippingRepository userShippingRepository;
	    private final UserRepository userRepository;

	    @Autowired
	    public UserShippingService(UserShippingRepository userShippingRepository, UserRepository userRepository) {
	        this.userShippingRepository = userShippingRepository;
	        this.userRepository = userRepository;
	    }
	    
	    public UserShipping getUserShipping(UserDetailsImpl userDetails) {
	        User user = userRepository.findById(userDetails.getId()).orElse(null);
	        if (user != null && user.getUserShippings() != null && !user.getUserShippings().isEmpty()) {
	            return user.getUserShippings().get(0); // Get the first UserShipping object from the collection
	        }
	        return null;
	    }


    
    public UserShipping createUserShipping(UserShipping userShipping) {
        return userShippingRepository.save(userShipping);
    }

 

    public UserShipping getUserShippingById(Long id) {
        return userShippingRepository.findById(id).orElse(null);
    }
    
  

    public List<UserShipping> getAllUserShippings() {
        return userShippingRepository.findAll();
    }

    public void deleteUserShipping(Long id) {
        userShippingRepository.deleteById(id);
    }
    public UserShipping updateUserShipping(Long id, UserShipping updatedUserShipping) {
        UserShipping existingUserShipping = userShippingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserShipping not found with id: " + id));
        
       
        existingUserShipping.setUserShippingCity(updatedUserShipping.getUserShippingCity());
        existingUserShipping.setUserShippingCountry(updatedUserShipping.getUserShippingCountry());
        existingUserShipping.setUserShippingDefault(updatedUserShipping.getUserShippingDefault());
        existingUserShipping.setUserShippingName(updatedUserShipping.getUserShippingName());
        existingUserShipping.setUserShippingState(updatedUserShipping.getUserShippingState());
        existingUserShipping.setUserShippingStreet1(updatedUserShipping.getUserShippingStreet1());
        existingUserShipping.setUserShippingStreet2(updatedUserShipping.getUserShippingStreet2());
        existingUserShipping.setUserShippingZipcode(updatedUserShipping.getUserShippingZipcode());
        
        return userShippingRepository.save(existingUserShipping);
    }
}
