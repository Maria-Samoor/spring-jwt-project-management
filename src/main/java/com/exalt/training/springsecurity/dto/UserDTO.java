package com.exalt.training.springsecurity.dto;

import com.exalt.training.springsecurity.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO representing the user request payload.
 */
@Data
public class UserDTO {
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Second name is required")
    @Size(max = 50, message = "Second name cannot exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull
    private Role role;
}