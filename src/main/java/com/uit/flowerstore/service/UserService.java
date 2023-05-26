package com.uit.flowerstore.service;

import java.util.Set;

import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.security.PasswordResetToken;
import com.uit.flowerstore.domain.security.UserRole;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail (String email);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	
}