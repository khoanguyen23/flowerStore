package com.uit.flowerstore.repository;
import com.uit.flowerstore.domain.CartItem;
import com.uit.flowerstore.domain.ShoppingCart;
import com.uit.flowerstore.domain.UserOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    List<CartItem> findByOrder(UserOrder order);
    void deleteByIdAndShoppingCart(Long id, ShoppingCart shoppingCart);
    void deleteById(Long id);
    @Modifying
    @Query("UPDATE CartItem c SET c.order = NULL WHERE c.id = :id")
    void updateCartItemOrderById(@Param("id") Long id);
}
