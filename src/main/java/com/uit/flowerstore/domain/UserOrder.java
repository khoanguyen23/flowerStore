package com.uit.flowerstore.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
	@Column(name = "order_total")
	private float orderTotal;
	@Column(name = "shipping_date")
	private String shippingDate;
	@Column(name = "shipping_method")
	private String shippingMethod;
	@Column(name = "billing_address_id")
	private int billingAddressId;
	@Column(name = "payment_id")
	private int paymentId;
	@Column(name = "shipping_address_id")
	private int shippingAddressId;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public UserOrder() {
		super();
	}
	public UserOrder(String  order_date, String order_status, float order_total, String shipping_date,
			String shipping_method, int billing_address_id, int payment_id, int shipping_address_id, User user) {
		super();
		this.orderDate = order_date;
		this.orderStatus = order_status;
		this.orderTotal = order_total;
		this.shippingDate = shipping_date;
		this.shippingMethod = shipping_method;
		this.billingAddressId = billing_address_id;
		this.paymentId = payment_id;
		this.shippingAddressId = shipping_address_id;
		this.user = user;
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public float getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public int getBillingAddressId() {
		return billingAddressId;
	}
	public void setBillingAddressId(int billingAddressId) {
		this.billingAddressId = billingAddressId;
	}
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public int getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(int shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "UserOrder [orderDate=" + orderDate + ", orderStatus=" + orderStatus + ", orderTotal=" + orderTotal
				+ ", shippingDate=" + shippingDate + ", shippingMethod=" + shippingMethod + ", billingAddressId="
				+ billingAddressId + ", paymentId=" + paymentId + ", shippingAddressId=" + shippingAddressId + ", user="
				+ user + "]";
	}
}
