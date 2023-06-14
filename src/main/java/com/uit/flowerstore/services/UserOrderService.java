package com.uit.flowerstore.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uit.flowerstore.domain.CartItem;
import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.UserOrder;
import com.uit.flowerstore.repository.CartItemRepository;
import com.uit.flowerstore.repository.UserOrderRepository;
import com.uit.flowerstore.repository.UserRepository;
import com.uit.flowerstore.security.services.UserDetailsImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    @Autowired
    public UserOrderService(UserOrderRepository userOrderRepository, UserRepository userRepository,CartItemRepository cartItemRepository) {
        this.userOrderRepository = userOrderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository =cartItemRepository;
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
        List<CartItem> cartItems = cartItemRepository.findByShoppingCart(user.getShoppingCart());
        userOrder.setUser(user);
		userOrder.setCartItems(cartItems);
		userOrder.updateOrderTotal();
		if (user.getUserPayments().isEmpty() == false) {
			for (int i = 0; i < user.getUserPayments().size(); i++) {
				if (user.getUserPayments().get(i).getDefaultPayment() == true) {
					userOrder.setUserPayment(user.getUserPayments().get(i));
				}
			}
		} else {
			throw new EntityNotFoundException("Chưa có Payment");
		}
		if (user.getUserShippings().isEmpty() == false) {
			userOrder.setUserShippingAddress(user.getUserShippings().get(0));
			for (int i = 0; i < user.getUserShippings().size(); i++) {
				if (user.getUserShippings().get(i).getUserShippingDefault() == true) {
					userOrder.setUserShippingAddress(user.getUserShippings().get(i));
				}
			}
		} else {
			throw new EntityNotFoundException("Chưa có Shipping Address");
		}
		return userOrderRepository.save(userOrder);
    }
    public UserOrder getOrderById(Long id, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
            return userOrderRepository.findByIdAndUser(id, user).orElse(null);
        }
        throw new EntityNotFoundException("Không tìm thấy người dùng");
    }
    public UserOrder getOrderByIdFromAdmin(Long id) {
            return userOrderRepository.findById(id).orElse(null);
    }
    @Transactional
    public void deleteOrder(Long id, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        if (user != null) {
        	List<CartItem> cartItems = cartItemRepository.findByOrder(this.getOrderByIdFromAdmin(id));
    		for(CartItem cartItem: cartItems) {
    			cartItem.setOrder(null);
    			cartItemRepository.save(cartItem);
    		}
            userOrderRepository.deleteByIdAndUser(id, user);
        }
    }

    public List<UserOrder> getAllOrders() {
        return userOrderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        userOrderRepository.deleteById(id);
    }

    public UserOrder updateOrderStatus(Long id, UserOrder updatedOrder) {
        UserOrder existingOrder = userOrderRepository.findById(id).orElse(null);
        existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
        if(updatedOrder.getOrderStatus().equals("Đang giao hàng")) {
        	existingOrder.setShippingDate(LocalDateTime.now().toString());
        }
        return userOrderRepository.save(existingOrder);
    }
    public UserOrder updateShippingMethod(Long id, UserOrder updatedOrder) {
        UserOrder existingOrder = userOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đơn hàng với id: " + id));
        existingOrder.setShippingMethod(updatedOrder.getShippingMethod());
        return userOrderRepository.save(existingOrder);
    }
}
