package com.exalt.training.springsecurity.controller;

import com.exalt.training.springsecurity.dto.JwtAuthenticationResponse;
import com.exalt.training.springsecurity.dto.RefreshTokenRequest;
import com.exalt.training.springsecurity.dto.SignUpRequest;
import com.exalt.training.springsecurity.dto.SigninRequest;
import com.exalt.training.springsecurity.exception.EmailAlreadyUsedException;
import com.exalt.training.springsecurity.model.User;
import com.exalt.training.springsecurity.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The AuthenticationController class handles user authentication requests such as
 * sign-up, sign-in, and token refresh.
 */
@RestController
@RequestMapping(path="exalt/training/security/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService; //AuthenticationService interface used to provide authentication service implementation methods

    /**
     * Handles user sign-up requests.
     *
     * @param signUpRequest the details of the user signing up
     * @return the signed-up user entity
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            User createdUser = authenticationService.signup(signUpRequest);
            Map<String, Object> response = new HashMap<>();
            response.put("id", createdUser.getId());
            response.put("firstName", createdUser.getFirstName());
            response.put("secondName", createdUser.getSecondName());
            response.put("email", createdUser.getEmail());
            response.put("password", createdUser.getPassword());
            response.put("role", createdUser.getRole().name());
            response.put("username", createdUser.getFirstName() + " " + createdUser.getSecondName());

            return ResponseEntity.ok(response);
        } catch (EmailAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Handles user sign-in requests.
     *
     * @param signinRequest the credentials for signing in
     * @return a JWT authentication response containing the token and user information
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest){
        try {
            JwtAuthenticationResponse jwtResponse = authenticationService.signin(signinRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /**
     * Handles JWT token refresh requests.
     *
     * @param refreshTokenRequest the request containing the refresh token
     * @return a JWT authentication response containing the new token and user information
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
