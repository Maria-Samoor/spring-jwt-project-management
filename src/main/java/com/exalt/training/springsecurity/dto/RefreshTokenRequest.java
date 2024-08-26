package com.exalt.training.springsecurity.dto;

import lombok.Data;

/**
 * A DTO representing the request for refreshing a JWT token.
 */
@Data
public class RefreshTokenRequest {
    private String token; //The current JWT refresh token that will be used to create a new token and refresh token.
}
