package com.bezkoder.springjwt.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;

import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;

import java.util.Collections;

//@Component
public class DataInitializer {//implements CommandLineRunner
/*
    private final RoleRepository roleRepository;


    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {

        this.roleRepository = roleRepository;

    }


 @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        Role ADMIN = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_ADMIN)));
        Role MODERATOR = roleRepository.findByName(ERole.ROLE_MODERATOR)
                .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_MODERATOR)));
        Role USER = roleRepository.findByName(ERole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_USER)));
    }*/
}
