
package com.uit.flowerstore.controller;

import com.uit.flowerstore.domain.CartItem;
import com.uit.flowerstore.domain.UserOrder;
import com.uit.flowerstore.security.services.UserDetailsImpl;
import com.uit.flowerstore.services.CartItemService;
import com.uit.flowerstore.services.FlowerService;
import com.uit.flowerstore.services.ShoppingCartService;
import com.uit.flowerstore.services.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class UserOrderController {
    private final UserOrderService userOrderService;
    private final CartItemService cartItemService;
    private final FlowerService flowerService;
    private final ShoppingCartService shoppingCartService;
    @Autowired
    public UserOrderController(UserOrderService userOrderService,CartItemService cartItemService,FlowerService flowerService, ShoppingCartService shoppingCartService) {
        this.userOrderService = userOrderService;
        this.cartItemService = cartItemService;
        this.flowerService = flowerService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/user-orders")
    public ResponseEntity<List<UserOrder>> getAllUserOrders(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<UserOrder> userOrders = userOrderService.getUserOrders(userDetails);
        if (!userOrders.isEmpty()) {
            return ResponseEntity.ok(userOrders);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user-orders/{id}")
    public ResponseEntity<UserOrder> getUserOrderById(@PathVariable("id") Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserOrder userOrder = userOrderService.getOrderById(id, userDetails);
        if (userOrder != null) {
            return ResponseEntity.ok(userOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user-orders")
    public ResponseEntity<UserOrder> createUserOrder(@RequestBody UserOrder userOrder, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((Authentication) principal).getPrincipal();
        List<CartItem> cartItems = cartItemService.getCartItemsByShoppingCart(userDetails.getShoppingCart());
        if(cartItems != null) {
        	 userOrder.setOrderDate(LocalDateTime.now().toString());
             userOrder.setShippingDate(LocalDateTime.now().plusDays(3).toString());
             UserOrder createdUserOrder = userOrderService.createOrder(userOrder, userDetails);
             userOrderService.updateOrderTotal(createdUserOrder,userDetails.getShoppingCart());
             cartItemService.createOrderAndAddCartItems(cartItems, createdUserOrder,flowerService);
             shoppingCartService.updateShoppingCart(userDetails.getShoppingCart(), userDetails);
             return ResponseEntity.status(HttpStatus.CREATED).body(createdUserOrder);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user-orders/{id}")
    public ResponseEntity<UserOrder> updateUserOrder(@PathVariable("id") Long id, @RequestBody UserOrder userOrder, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserOrder updatedUserOrder = userOrderService.updateOrder(id, userOrder, userDetails);
        if (updatedUserOrder != null) {
            return ResponseEntity.ok(updatedUserOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/user-orders/{id}")
    public ResponseEntity<Void> deleteUserOrder(@PathVariable("id") Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userOrderService.deleteOrder(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}
