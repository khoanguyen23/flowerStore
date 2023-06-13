package com.uit.flowerstore.repository;
import com.uit.flowerstore.domain.CartItem;
import com.uit.flowerstore.domain.ShoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    void deleteByIdAndShoppingCart(Long id, ShoppingCart shoppingCart);
    void deleteById(Long id);
}
