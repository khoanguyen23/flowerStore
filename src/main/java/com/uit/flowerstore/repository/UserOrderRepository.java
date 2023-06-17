package com.uit.flowerstore.repository;

import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    List<UserOrder> findAllByUser(User user);
    @Modifying
    @Query("DELETE FROM UserOrder u WHERE u.id = :id")
    void deleteUserOrderById(@Param("id") String id);
    @Query("SELECT o FROM UserOrder o WHERE o.id = :id")
    UserOrder findUserOrderById(@Param("id") String id);
    Optional<UserOrder> findByIdAndUser(Long id, User user);
    Optional<UserOrder> findById(Long id);
    void deleteByIdAndUser(Long id, User user);
}
