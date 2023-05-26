package com.uit.flowerstore.controller;

import java.util.Locale;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.security.PasswordResetToken;
import com.uit.flowerstore.domain.security.UserRole;
import com.uit.flowerstore.domain.security.Role;
import com.uit.flowerstore.service.UserService;
import com.uit.flowerstore.service.impl.UserSecurityService;
import com.uit.flowerstore.utility.MailConstructor;
import com.uit.flowerstore.utility.SecurityUtility;

import jakarta.servlet.http.HttpServletRequest;



public class ApplicationController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@GetMapping("/index")
	public String goHome() { 
		return "index";
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
		return "account";
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
	@GetMapping("/shop")
	public String shop() {
		return "shop";
	}
<<<<<<< Updated upstream

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	@GetMapping("/account-admin")
	public String accountAdmin() {
		return "account-admin";
=======
	@RequestMapping(value="/newUser", method = RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model
			) throws Exception{
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
		
		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			
			return "account";
		}
		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			
			return "account";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(email);
		
		model.addAttribute("emailSent", "true");
	
		
		return "account";
		
	}

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	
		
		return "account";
>>>>>>> Stashed changes
	}

}
