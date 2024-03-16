package com.bezkoder.springjwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	User findByEmail(String email);
    User findByResetPasswordToken(String resetToken);
    // Método para establecer el token de restablecimiento de contraseña
    default void setResetPasswordToken(User user, String resetToken) {
        user.setResetPasswordToken(resetToken);
        save(user);
    }
// Método para ver que usuario tiene que rol
@Query("SELECT u, r.name FROM User u LEFT JOIN u.roles r")
List<Object[]> getUsersWithRoleNames();
}
