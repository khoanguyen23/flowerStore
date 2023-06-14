package com.uit.flowerstore.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
@Entity
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "grand_total")
	private Double grandTotal;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@JsonBackReference
	@OneToMany(mappedBy = "shoppingCart",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;
	public ShoppingCart() {
		super();
	}
	public ShoppingCart(Double grandTotal, User user, List<CartItem> carts) {
		super();
		this.grandTotal = grandTotal;
		this.user = user;
		this.cartItems = carts;
	}
	public Long getId() {
		return id;
	}	
	public void setId(Long id) {
		this.id = id;
	}
	public Double getGrandTotal() {
		    return grandTotal;
	}
	public void setGrandTotal(Double grandTotal) {		
		this.grandTotal = grandTotal;
		updateGrandTotal();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
		updateGrandTotal();
	}
    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setShoppingCart(this);
    }
    
    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setShoppingCart(null);
    }
	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", grandTotal=" + grandTotal + ", user=" + user + "]";
	}
	
	public void updateGrandTotal() {
		this.grandTotal = 0.0;
		if(cartItems != null) {
			for (CartItem cartItem : cartItems) {
		    	this.grandTotal  += cartItem.getSubtotal();
		    }	
		}
	}
}
