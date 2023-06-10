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
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "grand_total")
	private Double grandTotal;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	public ShoppingCart() {
		super();
	}
	public ShoppingCart(Double grandTotal, User user) {
		super();
		this.grandTotal = grandTotal;
		this.user = user;
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
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", grandTotal=" + grandTotal + ", user=" + user + "]";
	}
}
