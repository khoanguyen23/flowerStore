package com.uit.flowerstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "user_shipping")
public class UserShipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_shipping_city")
    private String userShippingCity;

    @Column(name = "user_shipping_country")
    private String userShippingCountry;

//    @Column(name = "user_shipping_default")
//    private Boolean userShippingDefault;
    
    @Column(name = "user_shipping_default")
    private boolean userShippingDefault;

    @Column(name = "user_shipping_name")
    private String userShippingName;

    @Column(name = "user_shipping_state")
    private String userShippingState;

    @Column(name = "user_shipping_street1")
    private String userShippingStreet1;

    @Column(name = "user_shipping_street2")
    private String userShippingStreet2;

    @Column(name = "user_shipping_zipcode")
    private String userShippingZipcode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

	public UserShipping( String userShippingCity, String userShippingCountry, Boolean userShippingDefault,
			String userShippingName, String userShippingState, String userShippingStreet1, String userShippingStreet2,
			String userShippingZipcode, User user) {
		super();
	
		this.userShippingCity = userShippingCity;
		this.userShippingCountry = userShippingCountry;
		this.userShippingDefault = userShippingDefault;
		this.userShippingName = userShippingName;
		this.userShippingState = userShippingState;
		this.userShippingStreet1 = userShippingStreet1;
		this.userShippingStreet2 = userShippingStreet2;
		this.userShippingZipcode = userShippingZipcode;
		this.user = user;
	}
	
	public UserShipping() {
	   
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserShippingCity() {
		return userShippingCity;
	}

	public void setUserShippingCity(String userShippingCity) {
		this.userShippingCity = userShippingCity;
	}

	public String getUserShippingCountry() {
		return userShippingCountry;
	}

	public void setUserShippingCountry(String userShippingCountry) {
		this.userShippingCountry = userShippingCountry;
	}

	public Boolean getUserShippingDefault() {
		return userShippingDefault;
	}

	public void setUserShippingDefault(Boolean userShippingDefault) {
		this.userShippingDefault = userShippingDefault;
	}

	public String getUserShippingName() {
		return userShippingName;
	}

	public void setUserShippingName(String userShippingName) {
		this.userShippingName = userShippingName;
	}

	public void setUserShippingDefault(boolean userShippingDefault) {
		this.userShippingDefault = userShippingDefault;
	}

	public String getUserShippingState() {
		return userShippingState;
	}

	public void setUserShippingState(String userShippingState) {
		this.userShippingState = userShippingState;
	}

	public String getUserShippingStreet1() {
		return userShippingStreet1;
	}

	public void setUserShippingStreet1(String userShippingStreet1) {
		this.userShippingStreet1 = userShippingStreet1;
	}

	public String getUserShippingStreet2() {
		return userShippingStreet2;
	}

	public void setUserShippingStreet2(String userShippingStreet2) {
		this.userShippingStreet2 = userShippingStreet2;
	}

	public String getUserShippingZipcode() {
		return userShippingZipcode;
	}

	public void setUserShippingZipcode(String userShippingZipcode) {
		this.userShippingZipcode = userShippingZipcode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    

    // Constructors, getters, and setters
    
}
