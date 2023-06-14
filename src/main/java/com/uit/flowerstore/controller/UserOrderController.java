
package com.uit.flowerstore.controller;

import com.uit.flowerstore.domain.CartItem;
import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.UserOrder;
import com.uit.flowerstore.repository.UserRepository;
import com.uit.flowerstore.security.services.UserDetailsImpl;
import com.uit.flowerstore.services.CartItemService;
import com.uit.flowerstore.services.FlowerService;
import com.uit.flowerstore.services.ShoppingCartService;
import com.uit.flowerstore.services.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserOrderController {
    private final UserOrderService userOrderService;
    private final CartItemService cartItemService;
    private final FlowerService flowerService;
    private final ShoppingCartService shoppingCartService;
    private final UserRepository userRepository;
    @Autowired
    public UserOrderController(UserOrderService userOrderService,CartItemService cartItemService,FlowerService flowerService, ShoppingCartService shoppingCartService, UserRepository userRepository) {
        this.userOrderService = userOrderService;
        this.cartItemService = cartItemService;
        this.flowerService = flowerService;
        this.shoppingCartService = shoppingCartService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user-orders")
    public ResponseEntity<List<UserOrder>> getAllUserOrders(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        List<UserOrder> userOrders;
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        if(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
        	userOrders = userOrderService.getAllOrders(); 	
		}else {
			userOrders = userOrderService.getUserOrders(userDetails);
		}
        if (!userOrders.isEmpty()) {
            return ResponseEntity.ok(userOrders);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user-orders/{id}")
    public ResponseEntity<UserOrder> getUserOrderById(@PathVariable("id") Long id, Authentication authentication) {
    	UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        UserOrder userOrder = null;
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        if(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
        	userOrder = userOrderService.getOrderByIdFromAdmin(id);
        	
		}else {
			userOrder = userOrderService.getOrderById(id, userDetails);
		}
        if (userOrder != null) {
            return ResponseEntity.ok(userOrder);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user-orders")
    public ResponseEntity<UserOrder> createUserOrder(@RequestBody UserOrder userOrder, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((Authentication) principal).getPrincipal();
        List<CartItem> cartItems = cartItemService.getCartItemsByShoppingCart(userDetails.getShoppingCart());
        if(!cartItems.isEmpty()) {
        	 userOrder.setOrderDate(LocalDateTime.now().toString());
             userOrder.setShippingDate(LocalDateTime.now().plusDays(3).toString());
             UserOrder createdUserOrder = userOrderService.createOrder(userOrder, userDetails);
             cartItemService.createOrderAndAddCartItems(cartItems, createdUserOrder,flowerService);
             shoppingCartService.updateShoppingCart(userDetails.getShoppingCart(), cartItemService.getCartItemsByShoppingCart(userDetails.getShoppingCart()));
             return ResponseEntity.status(HttpStatus.CREATED).body(createdUserOrder);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user-orders/order-status/{id}")
    public ResponseEntity<UserOrder> updateUserOrderStatus(@PathVariable("id") Long id, @RequestBody UserOrder userOrder, Authentication authentication) {
    	UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        if(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
        	UserOrder updatedUserOrder = userOrderService.updateOrderStatus(id, userOrder);
        	if (updatedUserOrder != null) {
                return ResponseEntity.ok(updatedUserOrder);
            }
		}
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/user-orders/shipping-method/{id}")
    public ResponseEntity<UserOrder> updateUserOrderShippingMethod(@PathVariable("id") Long id, @RequestBody UserOrder userOrder, Authentication authentication) {
    	UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        if(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
        	UserOrder updatedUserOrder = userOrderService.updateShippingMethod(id, userOrder);
        	if (updatedUserOrder != null) {
                return ResponseEntity.ok(updatedUserOrder);
            }
		}
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/user-orders/{id}")
    public ResponseEntity<Void> deleteUserOrder(@PathVariable("id") Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userOrderService.deleteOrder(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
