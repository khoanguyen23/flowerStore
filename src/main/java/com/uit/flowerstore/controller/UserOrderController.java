//package com.uit.flowerstore.controller;
//
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.uit.flowerstore.domain.UserOrder;
//import com.uit.flowerstore.services.UserOrderService;
//
//	@CrossOrigin(origins = "http://localhost:8080")
//	@RestController
//	@RequestMapping("/api")
//	public class UserOrderController {
//		@Autowired
//		UserOrderService userOrderService;
//		@GetMapping("/user-orders")
//		public ResponseEntity<List<UserOrder>> getAllUserOrders() {
//		  List<UserOrder> userOrders = userOrderService.findAll();
//
//		  if (userOrders.isEmpty()) {
//		    return ResponseEntity.noContent().build();
//		  }
//
//		  return ResponseEntity.ok(userOrders);
//		}
//		@GetMapping("/user-orders/{id}")
//		  public ResponseEntity<UserOrder> getUserOrderById(@PathVariable("id") int id) {
//			UserOrder userOrder = userOrderService.findById(id);
//
//		    if (userOrder != null) {
//		      return new ResponseEntity<>(userOrder, HttpStatus.OK);
//		    } else {
//		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		    }
//		  }
//		@PostMapping("/user-orders")
//		  public ResponseEntity<UserOrder> createUserOrder(@RequestBody UserOrder userOrder) {
//		    try {
//		    	UserOrder _userOrder = userOrderService.save(new UserOrder(userOrder.getOrder_date(), userOrder.getOrder_status(),userOrder.getOrder_total(),userOrder.getShipping_date(),userOrder.getShipping_method(),userOrder.getBilling_address_id(),userOrder.getPayment_id(),userOrder.getShipping_address_id(),userOrder.getUser_id()));
//		      return new ResponseEntity<>(_userOrder, HttpStatus.CREATED);
//		    } catch (Exception e) {
//		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		    }
//		  }
//		@PutMapping("/user-orders/{id}")
//		  public ResponseEntity<UserOrder> updateUserOrder(@PathVariable("id") int id, @RequestBody UserOrder userOrder) {
//			UserOrder _userOrder = userOrderService.findById(id);
//
//		    if (_userOrder != null) {
//		    	_userOrder.setOrder_date(userOrder.getOrder_date().toString());
//		    	_userOrder.setOrder_status(userOrder.getOrder_status());
//		    	_userOrder.setOrder_total(userOrder.getOrder_total());
//		    	_userOrder.setShipping_date(userOrder.getShipping_date().toString());
//		    	_userOrder.setShipping_method(userOrder.getShipping_method());
//		    	_userOrder.setBilling_address_id(userOrder.getBilling_address_id());
//		    	_userOrder.setPayment_id(userOrder.getPayment_id());
//		    	_userOrder.setShipping_address_id(userOrder.getShipping_address_id());
//		    	_userOrder.setUser_id(userOrder.getUser_id());
//		      return new ResponseEntity<>( userOrderService.save(_userOrder), HttpStatus.OK);
//		    } else {
//		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		    }
//		  }
//
//		  @DeleteMapping("/user-orders/{id}")
//		  public ResponseEntity<HttpStatus> deleteUserOrder(@PathVariable("id") int id) {
//		    try {
//		    userOrderService.deleteById(id);
//		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		    } catch (Exception e) {
//		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		    }
//		  }
//
//		  @DeleteMapping("/user-orders")
//		  public ResponseEntity<HttpStatus> deleteAllUserOrders() {
//		    try {
//		    userOrderService.deleteAll();
//		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		    } catch (Exception e) {
//		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		    }
//
//		  }
//
//}
package com.uit.flowerstore.controller;

import com.uit.flowerstore.domain.UserOrder;
import com.uit.flowerstore.security.services.UserDetailsImpl;
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

    @Autowired
    public UserOrderController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
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
        userOrder.setOrderDate(LocalDateTime.now().toString());
        userOrder.setShippingDate(LocalDateTime.now().plusDays(3).toString());
        UserOrder createdUserOrder = userOrderService.createOrder(userOrder, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserOrder);
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
