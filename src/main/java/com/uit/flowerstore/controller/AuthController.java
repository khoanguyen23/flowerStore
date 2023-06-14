package com.uit.flowerstore.controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uit.flowerstore.domain.ERole;
import com.uit.flowerstore.domain.Role;
import com.uit.flowerstore.domain.User;
import com.uit.flowerstore.payload.request.ChangePasswordRequest;
import com.uit.flowerstore.payload.request.LoginRequest;
import com.uit.flowerstore.payload.request.SignupRequest;
import com.uit.flowerstore.payload.response.JwtResponse;
import com.uit.flowerstore.payload.response.MessageResponse;
import com.uit.flowerstore.repository.RoleRepository;
import com.uit.flowerstore.repository.UserRepository;
import com.uit.flowerstore.security.jwt.JwtUtils;
import com.uit.flowerstore.security.services.UserDetailsImpl;


import jakarta.persistence.EntityManager;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  
  @Autowired
  private EntityManager entityManager;
  
  

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), 
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
 
//  @PutMapping("users/{userId}")
//  public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
//    User user = userRepository.findById(userId)
//        .orElseThrow(() -> new RuntimeException("Error: User not found."));
//
//    user.setUsername(updatedUser.getUsername());
//    user.setEmail(updatedUser.getEmail());
//
//    userRepository.save(user);
//
//    return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
//  }
  @PutMapping("users/{userId}")
  public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new RuntimeException("Error: User not found."));

      // Kiểm tra và cập nhật username nếu có
      if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
          user.setUsername(updatedUser.getUsername());
      }

      // Kiểm tra và cập nhật email nếu có
      if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
          user.setEmail(updatedUser.getEmail());
      }
      // Kiểm tra và cập nhật sdt nếu có
      if (updatedUser.getTelephone() != null && !updatedUser.getTelephone().isEmpty()) {
          user.setTelephone(updatedUser.getTelephone());
      }
      // Kiểm tra và cập nhật sdt nếu có
      if (updatedUser.getFirstName() != null && !updatedUser.getFirstName().isEmpty()) {
          user.setFirstName(updatedUser.getFirstName());
      }
      // Kiểm tra và cập nhật sdt nếu có
      if (updatedUser.getLastName() != null && !updatedUser.getLastName().isEmpty()) {
          user.setLastName(updatedUser.getLastName());
      }

      userRepository.save(user);

      return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
  }

  
  @PutMapping("/users/{userId}/password")
  public ResponseEntity<?> changePassword(@PathVariable Long userId, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new RuntimeException("Error: User not found."));

      // Kiểm tra mật khẩu cũ
      if (!encoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Current password is incorrect."));
      }

      // Kiểm tra xác nhận mật khẩu mới
      if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
          return ResponseEntity.badRequest().body(new MessageResponse("Error: New password and confirm password do not match."));
      }

      // Đổi mật khẩu
      user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
      userRepository.save(user);

      return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
  }

  
  @DeleteMapping("/{userId}")
  public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
    
      if (!userRepository.existsById(userId)) {
          return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found."));
      }

     
      userRepository.deleteById(userId);

      return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
  }
  
 
}