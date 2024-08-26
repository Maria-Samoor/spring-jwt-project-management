package com.exalt.training.springsecurity.service;

import com.exalt.training.springsecurity.dto.JwtAuthenticationResponse;
import com.exalt.training.springsecurity.dto.RefreshTokenRequest;
import com.exalt.training.springsecurity.dto.SignUpRequest;
import com.exalt.training.springsecurity.dto.SigninRequest;
import com.exalt.training.springsecurity.model.User;

/**
 * Interface representing the service for handling authentication operations.
 */
public interface AuthenticationService {

    /**
     * Handles user sign-up based on the provided sign-up request.
     *
     * @param signUpRequest the sign-up request data.
     * @return the signed-up user.
     */
    User signup(SignUpRequest signUpRequest);
    /**
     * Handles user sign-in based on the provided sign-in request.
     *
     * @param signinRequest the sign-in request data.
     * @return the JWT authentication response with access and refresh tokens.
     */
    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    /**
     * Refreshes the JWT token based on the provided refresh token request.
     *
     * @param refreshTokenRequest the refresh token request data.
     * @return the refreshed JWT authentication response.
     */
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
