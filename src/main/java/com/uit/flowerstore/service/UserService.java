package com.uit.flowerstore.service;

import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.security.PasswordResetToken;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
}