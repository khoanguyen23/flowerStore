package com.uit.flowerstore.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_items")
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = User.class ,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="flower")
    private Flower flower;

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Flower getFlower() {
		return flower;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setFlower(Flower flower) {
		this.flower = flower;
	}
	public WishlistItem() {
		
	}

	public WishlistItem(Long id, User user, Flower flower) {
		super();
		this.id = id;
		this.user = user;
		this.flower = flower;
	}
    
    

	

    

    // Các constructor, getter, setter, ...
}

