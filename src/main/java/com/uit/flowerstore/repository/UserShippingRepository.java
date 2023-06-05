package com.uit.flowerstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uit.flowerstore.domain.UserShipping;

@Repository
public interface UserShippingRepository extends JpaRepository<UserShipping, Long> {
List<UserShipping> findAllByUserId(Long userId);
    
    Optional<UserShipping> findByIdAndUserId(Long id, Long userId);
    
    UserShipping save(UserShipping userShipping);
    List<UserShipping> findAllByUser_Id(Long userId);
    
    void deleteByIdAndUserId(Long id, Long userId);
}