package com.uit.flowerstore.services;
import com.uit.flowerstore.domain.CartItem;
import com.uit.flowerstore.domain.Flower;
import com.uit.flowerstore.domain.UserOrder;
import com.uit.flowerstore.domain.ShoppingCart;
import com.uit.flowerstore.repository.CartItemRepository;
import com.uit.flowerstore.repository.ShoppingCartRepository;
import com.uit.flowerstore.repository.UserOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserOrderRepository userOrderRepository;
    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ShoppingCartRepository shoppingCartRepository, UserOrderRepository userOrderRepository) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userOrderRepository = userOrderRepository;
    }
    public List<CartItem> getCartItemsByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }
    public List<CartItem> getCartItemsByOrder(Long id) {
        UserOrder order = userOrderRepository.findById(id).orElse(null);
        if (order != null) {
            return cartItemRepository.findByOrder(order);
        }
        return Collections.emptyList();
    }
    public CartItem createCartItem(CartItem cartItem, ShoppingCart shoppingCart,Flower flower) {
    	// Kiểm tra hoa đó đã có trong giỏ hàng chưa
    	for (CartItem cartItemTest : cartItemRepository.findByShoppingCart(shoppingCart)) {
    		if(cartItemTest.getFlower().getId() == flower.getId()) {
    			return null;
    		}
    	}
    	// kiểm tra còn hàng không
    	if(flower.getStock() == 0) return null;
    	cartItem.setShoppingCart(shoppingCart);
        cartItem.setFlower(flower);
        return cartItemRepository.save(cartItem);
    }
    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }
    @Transactional
    public void deleteCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        if (cartItem != null) {
            ShoppingCart shoppingCart = cartItem.getShoppingCart();
            if (shoppingCart != null) {
                shoppingCart.getCartItems().remove(cartItem);
                cartItem.setShoppingCart(null);
                shoppingCartRepository.save(shoppingCart);
            }
            cartItemRepository.delete(cartItem);
        }
    }
    public CartItem updateCartItem(Long id, CartItem updatedCartItem, FlowerService flowerService) {
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        if (cartItem != null) {
        	Flower flower = flowerService.getFlowerById(cartItem.getFlower().getId());
        	if(updatedCartItem.getQuantity() > flower.getStock()) return null;
            cartItem.setQuantity(updatedCartItem.getQuantity());
            cartItem.setFlower(flower);
            if(updatedCartItem.getQuantity() == 0) {
            	cartItem.setShoppingCart(null);
            }
            return cartItemRepository.save(cartItem);
        }
        return null;
    }
    public void createOrderAndAddCartItems(List<CartItem> cartItems, UserOrder order,FlowerService flowerService) {
    	for (CartItem cartItem : cartItems) {
    		cartItem.setOrder(order);  
            cartItem.setShoppingCart(null);  
    		Flower flower = cartItem.getFlower();
        	flower.decreaseStock(cartItem.getQuantity());
        	flowerService.updateFlower(flower.getId(), flower);
            cartItemRepository.save(cartItem);
        	}
        }
    public void deleteAllCart(List<CartItem> cartItems) {
    	for (CartItem cartItem : cartItems) {
        if (cartItem != null) {
            ShoppingCart shoppingCart = cartItem.getShoppingCart();
            if (shoppingCart != null) {
                shoppingCart.getCartItems().remove(cartItem);
                cartItem.setShoppingCart(null);
                shoppingCartRepository.save(shoppingCart);
            }
            cartItemRepository.delete(cartItem);
        }
        }
    }
    
}
