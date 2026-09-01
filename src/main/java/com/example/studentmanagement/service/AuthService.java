package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.AuthRequest;
import com.example.studentmanagement.dto.AuthResponse;
import com.example.studentmanagement.entity.AppUser;
import com.example.studentmanagement.repository.AppUserRepository;
import com.example.studentmanagement.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AppUserRepository userRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService,
            AppUserRepository userRepository) {

        this.authenticationManager =
                authenticationManager;

        this.userDetailsService =
                userDetailsService;

        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public AuthResponse login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetails userDetails =
                userDetailsService
                        .loadUserByUsername(
                                request.username()
                        );

        String token =
                jwtService.generateToken(
                        userDetails
                );

        AppUser user =
                userRepository
                        .findByUsername(
                                request.username()
                        )
                        .orElseThrow();

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
}