package com.bezkoder.springjwt.controllers;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bezkoder.springjwt.models.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


	@GetMapping("/usuarios")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> listAll() {
		return ResponseEntity.ok(userRepository.findAll());
	}

	@GetMapping("/usersWithRoles")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Object[]>> getUsersWithRoles() {
		List<Object[]> usersWithRoles = userRepository.getUsersWithRoleNames();
		return ResponseEntity.ok(usersWithRoles);
	}
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> listById(@PathVariable Long id) {
		return ResponseEntity.ok(userRepository.findById(id));
	}
	@GetMapping("/roles")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> listAllrol() {
		return ResponseEntity.ok(roleRepository.findAll());
	}
	@GetMapping("/role/{id}")
	public ResponseEntity<?> listRolById(@PathVariable Long id) {
		return ResponseEntity.ok(roleRepository.findById(id));
	}
	@DeleteMapping("/usuario/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			User user = userRepository.findById(id).orElse(null);
			if (user == null) {
				response.put("mensaje", "Error: no se pudo eliminar, el usuario ID: " + id + " no existe en la base de datos.");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			userRepository.delete(user);

			response.put("mensaje", "Usuario eliminado correctamente.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("mensaje", "Error interno del servidor.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/usuario/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
		Map<String, Object> response = new HashMap<>();
		try {
			User usuarioActual = userRepository.findById(id).orElse(null);
			if (usuarioActual == null) {
				response.put("mensaje", "Error: no se pudo editar, el usuario ID: " + id + " no existe en la base de datos.");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			// Actualizar campos del usuario actual con los valores proporcionados
			usuarioActual.setUsername(user.getUsername());
			usuarioActual.setEmail(user.getEmail());

			// Si se proporcionó una nueva contraseña, encriptarla y actualizarla
			if (user.getPassword() != null && !user.getPassword().isEmpty()) {
				usuarioActual.setPassword(encoder.encode(user.getPassword()));
				usuarioActual.setResetPasswordToken("Se reinició la contraseña");
			}

			// Eliminar los roles anteriores del usuario
			usuarioActual.getRoles().clear();

			// Obtener los nuevos roles del request y agregarlos al usuario
			Set<Role> nuevosRoles = new HashSet<>();
			for (Role role : user.getRoles()) {
				Role fetchedRole = roleRepository.findByName(role.getName())
						.orElseThrow(() -> new RuntimeException("Error: Role not found."));
				nuevosRoles.add(fetchedRole);
				System.out.println("fetchedRole = " + fetchedRole);
			}
			usuarioActual.setRoles(nuevosRoles);
			System.out.println("nuevosRoles = " + nuevosRoles);
			// Guardar el usuario actualizado
			User usuarioActualizado = userRepository.save(usuarioActual);

			response.put("mensaje", "El usuario ha sido actualizado con éxito.");
			response.put("usuario", usuarioActualizado);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.put("mensaje", "Error interno del servidor.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}





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
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
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
		user.setResetPasswordToken("Se reinicio la contraseña");
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
	}
}
