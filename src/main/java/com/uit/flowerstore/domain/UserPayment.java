package com.uit.flowerstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "user_payment")
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "cvc")
    private int cvc;

    @Column(name = "default_payment")
    private boolean defaultPayment;

    public void setDefaultPayment(boolean defaultPayment) {
		this.defaultPayment = defaultPayment;
	}

	@Column(name = "expiry_month")
    private int expiryMonth;

    @Column(name = "expiry_year")
    private int expiryYear;

    @Column(name = "holder_name")
    private String holderName;

    @Column(name = "type")
    private String type;
    
    public UserPayment() {
        
    }

    
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

	public UserPayment(Long id, String cardName, String cardNumber, int cvc, Boolean defaultPayment, int expiryMonth,
			int expiryYear, String holderName, String type, User user) {
		super();
		this.id = id;
		this.cardName = cardName;
		this.cardNumber = cardNumber;
		this.cvc = cvc;
		this.defaultPayment = defaultPayment;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.holderName = holderName;
		this.type = type;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public String getCardName() {
		return cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public int getCvc() {
		return cvc;
	}

	public Boolean getDefaultPayment() {
		return defaultPayment;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public String getHolderName() {
		return holderName;
	}

	public String getType() {
		return type;
	}

	public User getUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setCvc(int cvc) {
		this.cvc = cvc;
	}

	public void setDefaultPayment(Boolean defaultPayment) {
		this.defaultPayment = defaultPayment;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUser(User user) {
		this.user = user;
	}


    
}

