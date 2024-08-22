package com.exalt.training.springsecurity.service;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

/**
 * Interface representing the service for handling JWT operations.
 */
public interface JWTService {

    /**
     * Generates a JWT token based on the provided user details.
     *
     * @param userDetails the user details for generating the token.
     * @return the generated JWT token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    String extractUserName(String token);

    /**
     * Validates whether the provided JWT token is still valid for the given user details.
     *
     * @param token the JWT token to validate.
     * @param userDetails the user details to compare against.
     * @return true if the token is valid; otherwise, false.
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Generates a refresh token with extra claims and the provided user details.
     *
     * @param extraClaims additional claims to include in the token.
     * @param userDetails the user details for generating the refresh token.
     * @return the generated refresh token.
     */
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
