package com.uit.flowerstore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "qty")
	private int quantity;
	@Column(name = "subtotal")
	private double subtotal;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flowerstore_id")
    private Flower flower;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private UserOrder order;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopping_cart_id")
	private ShoppingCart shoppingCart;
	public CartItem() {
		super();
	}
	public CartItem(int quantity, double subtotal, Flower flower, UserOrder order, ShoppingCart shoppingCart) {
		super();
		this.quantity = quantity;
		this.subtotal = subtotal;
//		this.subtotal = quantity * (flower.getPrice()- flower.getPrice()*(flower.getDiscount()/100));
		this.flower = flower;
		this.order = order;
		this.shoppingCart = shoppingCart;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		updateSubTotal();
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Flower getFlower() {
		return flower;
	}
	public void setFlower(Flower flower) {
		this.flower = flower;
		updateSubTotal();
	}
	public UserOrder getOrder() {
		return order;
	}
	public void setOrder(UserOrder order) {
		this.order = order;
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@Override
	public String toString() {
		return "CartItem [quantity=" + quantity + ", subtotal=" + subtotal + ", flower=" + flower + ", order=" + order
				+ ", shoppingCart=" + shoppingCart + "]";
	}
	public void updateSubTotal() {
		if (flower != null && flower.getPrice() != null && flower.getDiscount()!= null) {
	        subtotal = quantity * (flower.getPrice()-flower.getPrice()*flower.getDiscount()/100);
	    } else {
	    	subtotal = 0;
	    }
	}
}
