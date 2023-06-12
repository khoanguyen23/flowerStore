package com.uit.flowerstore.repository;

import com.uit.flowerstore.domain.ShoppingCart;
import com.uit.flowerstore.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUserId(Long userId);
    Optional<ShoppingCart> findByIdAndUserId(Long id, Long userId);

    void deleteByUser(User user);
}
