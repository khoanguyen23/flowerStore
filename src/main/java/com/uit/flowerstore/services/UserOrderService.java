package com.uit.flowerstore.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uit.flowerstore.domain.ShoppingCart;
import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.UserOrder;
import com.uit.flowerstore.repository.UserOrderRepository;
import com.uit.flowerstore.repository.UserRepository;
import com.uit.flowerstore.security.services.UserDetailsImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserOrderService(UserOrderRepository userOrderRepository, UserRepository userRepository) {
        this.userOrderRepository = userOrderRepository;
        this.userRepository = userRepository;
    }

    public List<UserOrder> getUserOrders(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            return userOrderRepository.findAllByUser(user);
        }
        return Collections.emptyList();
    }
    public UserOrder createOrder(UserOrder userOrder, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            userOrder.setUser(user);
            if(user.getUserPayments().isEmpty() == false) {
            	for(int i = 0; i < user.getUserPayments().size();i++) {
            		if(user.getUserPayments().get(i).getDefaultPayment() == true) {
            			userOrder.setUserPayment(user.getUserPayments().get(i));
            		}
            	}
            }else {
            	throw new EntityNotFoundException("Chưa có Payment");
            }
            if(user.getUserShippings().isEmpty() == false) {
            	userOrder.setUserShippingAddress(user.getUserShippings().get(0));
            	for(int i = 0; i < user.getUserShippings().size();i++) {
            		if(user.getUserShippings().get(i).getUserShippingDefault() == true) {
            			userOrder.setUserShippingAddress(user.getUserShippings().get(i));
            		}
            	}
            }else {
            	throw new EntityNotFoundException("Chưa có Shipping Address");
            }
            return userOrderRepository.save(userOrder);
        }
        throw new EntityNotFoundException("User not found");
    }
    public UserOrder getOrderById(Long id, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            return userOrderRepository.findByIdAndUser(id, user).orElse(null);
        }
        throw new EntityNotFoundException("Không tìm thấy người dùng");
    }
    @Transactional
    public void deleteOrder(Long id, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            userOrderRepository.deleteByIdAndUser(id, user);
        }
    }

    public UserOrder updateOrder(Long id, UserOrder updatedOrder, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            UserOrder existingOrder = userOrderRepository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng với id: " + id));
            existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
            return userOrderRepository.save(existingOrder);
        }
        return null;
    }

    public List<UserOrder> getAllOrders() {
        return userOrderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        userOrderRepository.deleteById(id);
    }

    public UserOrder updateOrder(Long id, UserOrder updatedOrder) {
        UserOrder existingOrder = userOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng với id: " + id));

        existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
        return userOrderRepository.save(existingOrder);
    }
    public void updateOrderTotal(UserOrder userOrder, ShoppingCart shoppingCart) {
        userOrder.setOrderTotal(shoppingCart.getGrandTotal());
        userOrderRepository.save(userOrder);
    }
}
