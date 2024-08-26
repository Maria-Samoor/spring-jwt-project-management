package com.exalt.training.springsecurity.dto;

import lombok.Data;

/**
 * A DTO representing the sign-in request payload.
 */
@Data
public class SigninRequest {
    private String email; //user email address
    private String password; //user password
}
