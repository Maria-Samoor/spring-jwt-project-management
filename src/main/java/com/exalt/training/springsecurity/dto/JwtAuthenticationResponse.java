package com.exalt.training.springsecurity.dto;

import lombok.Data;

/**
 * A DTO representing the response for JWT authentication.
 */
@Data
public class JwtAuthenticationResponse {
    private String token; //The JWT token issued after successful authentication.
    private String refreshToken; //The refresh token for renewing the JWT when it expires.
}
