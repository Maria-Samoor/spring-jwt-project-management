package com.exalt.training.springsecurity.service;

import com.exalt.training.springsecurity.dto.ProjectDTO;
import com.exalt.training.springsecurity.model.Project;

import java.util.List;

/**
 * Service interface for handling business logic related to projects.
 * Defines methods for creating, updating, retrieving, and deleting projects by title.
 */
public interface ProjectService {

    /**
     * Creates a new project with the given DTO.
     *
     * @param projectDTO the DTO containing project details
     * @return the created Project entity
     */
    Project createProject(ProjectDTO projectDTO);

    /**
     * Updates an existing project with the given DTO based on its title.
     *
     * @param title the title of the project to update
     * @param projectDTO the DTO containing updated project details
     * @return the updated Project entity
     * @throws IllegalArgumentException if the project is not found
     */
    Project updateProject(String title, ProjectDTO projectDTO);

    /**
     * Updates the status of an existing project based on its title.
     *
     * @param title the title of the project to update
     * @param status the new status
     * @return the updated Project entity
     * @throws IllegalArgumentException if the project is not found
     */
    Project updateProjectStatus(String title, String status);

    /**
     * Retrieves all projects.
     *
     * @return a list of all Project entities
     */
    List<Project> getAllProjects();

    /**
     * Retrieves a project by its title.
     *
     * @param title the title of the project
     * @return the Project entity
     * @throws IllegalArgumentException if no project with the given title is found
     */
    Project getProjectByTitle(String title);

    /**
     * Deletes a project by its title.
     *
     * @param title the title of the project to delete
     * @throws IllegalArgumentException if the project is not found
     */
    void deleteProjectByTitle(String title);
}
