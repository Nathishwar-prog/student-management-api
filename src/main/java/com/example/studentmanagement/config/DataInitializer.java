package com.example.studentmanagement.config;

import com.example.studentmanagement.entity.AppUser;
import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.repository.AppUserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            AppUserRepository repository,
            PasswordEncoder encoder) {

        return args -> {

            if (repository
                    .findByUsername("admin")
                    .isEmpty()) {

                repository.save(
                        new AppUser(
                                "admin",
                                encoder.encode("admin123"),
                                Role.ROLE_ADMIN
                        )
                );
            }

            if (repository
                    .findByUsername("user")
                    .isEmpty()) {

                repository.save(
                        new AppUser(
                                "user",
                                encoder.encode("user123"),
                                Role.ROLE_USER
                        )
                );
            }
        };
    }
}