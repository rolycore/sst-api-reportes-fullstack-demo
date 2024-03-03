package com.bezkoder.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600)//https://appicmlab.icmetrologia.com
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

		String role = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (role == null || role.isEmpty()) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			String[] roleArray = role.split(",");
			for (String r : roleArray) {
				Role userRole = roleRepository.findByName(ERole.valueOf("ROLE_" + r.toUpperCase()))
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	@PostMapping("/create-admin")
	public ResponseEntity<?> createAdminUser() {
		if (userRepository.existsByUsername("admin")) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Admin user already exists!"));
		}

		// Create the admin user
		User adminUser = new User("admin", "admin@example.com", encoder.encode("adminPassword"));

		Set<Role> roles = new HashSet<>();
		Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Error: Admin role not found."));
		roles.add(adminRole);
		adminUser.setRoles(roles);

		userRepository.save(adminUser);

		return ResponseEntity.ok(new MessageResponse("Admin user created successfully!"));
	}

	@PutMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> resetRequest) {
		String email = resetRequest.get("email");
		String newPassword = resetRequest.get("newPassword");

		if (email == null || newPassword == null) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Invalid request format."));
		}

		// Obtén el usuario por su dirección de correo electrónico
		User user = userRepository.findByEmail(email);

		if (user == null) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email not found."));
		}

		// Cambia la contraseña del usuario
		user.setPassword(encoder.encode(newPassword));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
	}
}
