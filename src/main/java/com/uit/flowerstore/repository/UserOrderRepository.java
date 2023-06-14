package com.uit.flowerstore.repository;

import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
    List<UserOrder> findAllByUser(User user);

    Optional<UserOrder> findByIdAndUser(Long id, User user);
    Optional<UserOrder> findById(Long id);
    void deleteByIdAndUser(Long id, User user);
}
