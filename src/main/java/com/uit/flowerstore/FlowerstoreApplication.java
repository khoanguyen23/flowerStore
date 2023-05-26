package com.uit.flowerstore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uit.flowerstore.service.UserService;
import com.uit.flowerstore.utility.SecurityUtility;

import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.domain.security.Role;
import com.uit.flowerstore.domain.security.UserRole;

@SpringBootApplication
public class FlowerstoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FlowerstoreApplication.class, args);
	}
	@Autowired
	private UserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("Nguyen");
		user1.setLastName("Khoa");
		user1.setUsername("nguyenkhoa23");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user1.setEmail("khoahoang151@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1= new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));
		
		userService.createUser(user1, userRoles);
	}
	

}
