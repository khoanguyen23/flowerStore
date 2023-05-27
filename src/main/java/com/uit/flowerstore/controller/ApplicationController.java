package com.uit.flowerstore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	@GetMapping("/shop")
	public String shop() {
		return "shop";
	}
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	@GetMapping("/index-2")
	public String index2() {
		return "index-2";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/checkout")
	public String checkout() {
		return "checkout";
	}
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	@GetMapping("/faq")
	public String faq() {
		return "faq";
	}
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	@GetMapping("/locations")
	public String locations() {
		return "locations";
	}
	@GetMapping("/order-tracking")
	public String orderTracking() {
		return "order-tracking";
	}


	@GetMapping("/account")
	public String account() {
		return "account";
	}

	@GetMapping("/wishlist")
	public String wishlist() {
		return "wishlist";
	}
	@GetMapping("/product-details")
	public String productDetails() {
		return "product-details";
	}
	@GetMapping("/portfolio")
	public String portfolio() {
		return "portfolio";
	}
	

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	@GetMapping("/account-admin")
	public String accountAdmin() {
		return "account-admin";
	}
}
