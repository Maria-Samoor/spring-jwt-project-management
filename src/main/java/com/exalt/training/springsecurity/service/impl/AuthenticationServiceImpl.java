package com.exalt.training.springsecurity.service.impl;

import com.exalt.training.springsecurity.dto.JwtAuthenticationResponse;
import com.exalt.training.springsecurity.dto.RefreshTokenRequest;
import com.exalt.training.springsecurity.dto.SignUpRequest;
import com.exalt.training.springsecurity.dto.SigninRequest;
import com.exalt.training.springsecurity.exception.EmailAlreadyUsedException;
import com.exalt.training.springsecurity.model.Role;
import com.exalt.training.springsecurity.model.User;
import com.exalt.training.springsecurity.repository.UserRepository;
import com.exalt.training.springsecurity.service.AuthenticationService;
import com.exalt.training.springsecurity.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Implementation of the AuthenticationService interface, providing authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    /**
     * Handles user sign-up based on the provided sign-up request.
     *
     * @param signUpRequest the sign-up request data.
     * @return the signed-up user.
     */
    public User signup( SignUpRequest signUpRequest){
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException("Email is already used");
        }
        User user= new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setSecondName(signUpRequest.getLastName());
        user.setRole(Role.TeamMember);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Authenticates a user based on their email and password, generates a JWT token, and returns a response containing both the JWT token and a refresh token.
     *
     * @param signinRequest The request containing the user's email and password.
     * @return A {@link JwtAuthenticationResponse} containing the JWT token and refresh token.
     * @throws IllegalArgumentException if the credentials provided are invalid.
     */
    public JwtAuthenticationResponse signin(SigninRequest signinRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid Credentials", e);
        }
        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid Credentials")); // Retrieve the user from the repository based on their email.
        var jwt = jwtService.generateToken(user); // Generate the JWT token for the authenticated user.
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user); // Generate a refresh token for the authenticated user.

        // Create and return the response containing the JWT and refresh token.
        JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    /**
     * Refreshes the JWT token for a user based on a valid refresh token.
     *
     * @param refreshTokenRequest The request containing the refresh token.
     * @return A {@link JwtAuthenticationResponse} containing a new JWT token and the existing refresh token, or {@code null} if the token is invalid.
     */
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken()); //Extract the username (email) from the refresh token.
        User user = userRepository.findByEmail(userEmail).orElseThrow(); // Retrieve the user from the repository based on the extracted email.

        // Validate the token. If valid, generate a new JWT token.
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            // Create and return the response containing the new JWT token and the existing refresh token.
            JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
