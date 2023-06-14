package com.uit.flowerstore.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class UserOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "order_date")
	private String orderDate;
	@Column(name = "order_status")
	private String orderStatus;
	@ElementCollection
    private List<String> shippingMethods;
	@Column(name = "order_total")
	private double orderTotal;
	@Column(name = "shipping_date")
	private String shippingDate;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "payment_id")
	private UserPayment userPayment;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shipping_address_id")
	private UserShipping userShippingAddress;
//	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public UserOrder() {
		super();
	}
	public UserOrder(String orderDate, String orderStatus, double orderTotal, String shippingDate,
			List<String> shippingMethods, UserPayment userPayment, UserShipping userShippingAddress, User user) {
		super();
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
		this.shippingDate = shippingDate;
		this.shippingMethods = shippingMethods;
		this.userPayment = userPayment;
		this.userShippingAddress = userShippingAddress;
		this.user = user;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public UserPayment getUserPayment() {
		return userPayment;
	}

	public void setUserPayment(UserPayment userPayment) {
		this.userPayment = userPayment;
	}

	public UserShipping getUserShippingAddress() {
		return userShippingAddress;
	}

	public void setUserShippingAddress(UserShipping userShippingAddress) {
		this.userShippingAddress = userShippingAddress;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getShippingMethod() {
		return shippingMethods;
	}
	public void setShippingMethod(List<String> shippingMethod) {
		this.shippingMethods = shippingMethod;
	}
	@Override
	public String toString() {
		return "UserOrder [orderDate=" + orderDate + ", orderStatus=" + orderStatus + ", orderTotal=" + orderTotal
				+ ", shippingDate=" + shippingDate + ", shippingMethod=" + orderStatus + ", userPayment="
				+ userPayment + ", userShippingAddress=" + userShippingAddress + ", user=" + user + "]";
	}
	@JsonBackReference
	@OneToMany(mappedBy = "order",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public void updateOrderTotal() {
		this.orderTotal = 0.0;
		for(CartItem cartItem: this.cartItems) {
			this.orderTotal += cartItem.getSubtotal();
		}
	}
}
