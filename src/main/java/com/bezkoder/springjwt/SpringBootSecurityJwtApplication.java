package com.bezkoder.springjwt;

import com.bezkoder.springjwt.auditoria.config.PersistenceConfig;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import static com.bezkoder.springjwt.models.ERole.ROLE_ADMIN;
import static com.bezkoder.springjwt.models.ERole.ROLE_MODERATOR;
@SpringBootApplication
@Import(PersistenceConfig.class)
public class SpringBootSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}


}