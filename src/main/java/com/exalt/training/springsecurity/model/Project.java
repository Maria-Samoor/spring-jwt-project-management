package com.exalt.training.springsecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a project in the system.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="projects")
public class Project {
    @Id
    @SequenceGenerator(
            name="projects_sequence",
            sequenceName="projects_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "projects_sequence"
    )
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id; // identifier for the project
    @Column(name = "title", nullable = false,unique = true, length = 100)
    private String title; // project title
    @Column(name = "company", nullable = false, length = 100)
    private String company; // project title
    @Column(name = "description", length = 500)
    private String description; // project details
    @Column(name = "status", nullable = false)
    private String status; // project status (completed, pending, etc.)
}
