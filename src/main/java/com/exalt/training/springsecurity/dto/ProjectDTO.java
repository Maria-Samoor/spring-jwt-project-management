package com.exalt.training.springsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * A Data Transfer Object (DTO) representing a project.
 */
@Data
public class ProjectDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters long")
    private String title;

    @NotBlank(message = "Company is required")
    @Size(max = 100, message = "Company must be at most 100 characters long")
    private String company;

    @Size(max = 500, message = "Description must be at most 500 characters long")
    private String description;

    @NotBlank(message = "Status is required")
    private String status;

}
