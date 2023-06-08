package com.uit.flowerstore.domain;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "flower")
public class Flower {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
	private String name;
	private String offerEnd;
	
	
	private String shortDescription;
	private String fullDescription;
	@ElementCollection
    private List<String> image;
	
	public String getFullDescription() {
		return fullDescription;
	}
	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}
	@ElementCollection
    private List<String> category;
	@ElementCollection
    private List<String> tag;
	
	
	private Integer stock;
	private Integer price;
	public List<String> getTag() {
		return tag;
	}
	public void setTag(List<String> tag) {
		this.tag = tag;
	}
	private Integer discount;
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOfferEnd() {
		return offerEnd;
	}
	public void setOfferEnd(String offerEnd) {
		this.offerEnd = offerEnd;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}
	public List<String> getCategory() {
		return category;
	}
	public void setCategory(List<String> category) {
		this.category = category;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
