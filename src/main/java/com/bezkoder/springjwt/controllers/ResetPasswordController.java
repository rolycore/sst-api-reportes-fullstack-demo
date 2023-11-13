package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.impl.impl.EmailService;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
@CrossOrigin(origins = { "https://appicmlab.icmetrologia.com" })//https://appicmlab.icmetrologia.com
@RestController
@RequestMapping("/api/v1/reset-password")
@PreAuthorize("hasRole('ADMIN')")
public class ResetPasswordController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Validar el correo electrónico y obtener el usuario
        User user = userDetailsService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }


        // Generar un token de restablecimiento de contraseña (usando JwtUtils)
        String resetToken = jwtUtils.generateJwtTokenForPasswordReset(user);


        // Enviar un correo electrónico con un enlace que incluya el token
        sendResetPasswordEmail(user, resetToken);

        return ResponseEntity.ok("Se ha enviado un correo electrónico para restablecer la contraseña.");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
       User user = userRepository.findByResetPasswordToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido");
        }
        // Validar y actualizar la contraseña del usuario
       user.setPassword(newPassword);
       user.setResetPasswordToken(null); // Borra el token
        userRepository.save(user);
        return ResponseEntity.ok("Contraseña restablecida con éxito");
    }


    // Método para enviar el correo electrónico
    private void sendResetPasswordEmail(User user, String resetToken) {
        String subject = "Restablecer contraseña";
        String message = "Haz clic en el siguiente enlace para restablecer tu contraseña: " +
                "http://localhost:4200/api/v1/reset-password?token=" + resetToken;

        emailService.sendEmail(user.getEmail(), subject, message);
    }

}
