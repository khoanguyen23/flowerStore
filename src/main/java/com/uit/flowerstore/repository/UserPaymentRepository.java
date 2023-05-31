package com.uit.flowerstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uit.flowerstore.domain.UserPayment;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
}
