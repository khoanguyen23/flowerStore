package com.uit.flowerstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.uit.flowerstore.domain.User;



public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	User findByEmail(String email);
}