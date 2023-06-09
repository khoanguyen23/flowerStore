package com.uit.flowerstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uit.flowerstore.domain.UserPayment;
import com.uit.flowerstore.domain.UserShipping;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
	List<UserPayment> findAllByUserId(Long userId);
	Optional<UserPayment> findByIdAndUserId(Long id , Long userId);
	UserPayment save(UserPayment userPayment);
	List<UserPayment> findAllByUser_Id(Long userId);
	
	void deleteByIdAndUserId(Long id, Long userId);
	
}

