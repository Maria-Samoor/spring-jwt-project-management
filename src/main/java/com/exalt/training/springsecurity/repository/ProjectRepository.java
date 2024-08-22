package com.exalt.training.springsecurity.repository;

import com.exalt.training.springsecurity.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for handling CRUD operations on Project entities.
 * Extends JpaRepository to leverage JPA's built-in methods.
 */
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    /**
     * Finds a project by its title.
     *
     * @param title the title of the project
     * @return an Optional containing the found Project, or empty if not found
     */
    Optional<Project> findByTitle(String title);

}
