package com.uit.flowerstore.services;
import com.uit.flowerstore.domain.CartItem;
import com.uit.flowerstore.domain.Flower;
import com.uit.flowerstore.domain.UserOrder;
import com.uit.flowerstore.domain.ShoppingCart;
import com.uit.flowerstore.repository.CartItemRepository;
import com.uit.flowerstore.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ShoppingCartRepository shoppingCartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public List<CartItem> getCartItemsByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }
    public CartItem createCartItem(CartItem cartItem, ShoppingCart shoppingCart,Flower flower) {
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setFlower(flower);
        return cartItemRepository.save(cartItem);
    }
    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
    public CartItem updateCartItem(Long id, CartItem updatedCartItem, FlowerService flowerService) {
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(updatedCartItem.getQuantity());
            Flower flower = flowerService.getFlowerById(cartItem.getFlower().getId());
            cartItem.setFlower(flower);
            return cartItemRepository.save(cartItem);
        }
        return null;
    }
    public void createOrderAndAddCartItems(List<CartItem> cartItems, UserOrder order) {
        for (CartItem cartItem : cartItems) {
            cartItem.setOrder(order);
            cartItem.setShoppingCart(null);
            cartItemRepository.save(cartItem);
        }
    }
}
