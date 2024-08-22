package com.exalt.training.springsecurity.service.impl;
import com.exalt.training.springsecurity.dto.ProjectDTO;
import com.exalt.training.springsecurity.exception.ProjectTitleAlreadyExistsException;
import com.exalt.training.springsecurity.model.Project;
import com.exalt.training.springsecurity.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exalt.training.springsecurity.service.ProjectService;

import java.util.List;

/**
 * Implementation of the ProjectService interface.
 * Contains the business logic for handling projects, including CRUD operations based on the project title.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Creates a new project based on the provided {@link ProjectDTO}.
     *
     * @param projectDTO the data transfer object containing the project details
     * @return the created {@link Project} entity
     */
    @Override
    public Project createProject(ProjectDTO projectDTO) {
        if (projectRepository.findByTitle(projectDTO.getTitle()).isPresent()) {
            throw new ProjectTitleAlreadyExistsException("Project title already exists: " + projectDTO.getTitle());
        }
        Project project = new Project();
        project.setTitle(projectDTO.getTitle());
        project.setCompany(projectDTO.getCompany());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus());
        return projectRepository.save(project);
    }

    /**
     * Updates an existing project based on its title with the details provided in {@link ProjectDTO}.
     *
     * @param title the title of the project to be updated
     * @param projectDTO the data transfer object containing the updated project details
     * @return the updated {@link Project} entity
     * @throws IllegalArgumentException if no project with the given title is found
     */
    @Override
    public Project updateProject(String title, ProjectDTO projectDTO) {
        Project project = projectRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        if (projectRepository.findByTitle(projectDTO.getTitle()).isPresent()) {
            throw new ProjectTitleAlreadyExistsException("Project with this title already exists: " + projectDTO.getTitle());
        }
        project.setTitle(projectDTO.getTitle());
        project.setCompany(projectDTO.getCompany());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus());
        return projectRepository.save(project);
    }

    /**
     * Updates the status of an existing project based on its title with the new status provided in {@link ProjectDTO}.
     *
     * @param title the title of the project whose status is to be updated
     * @param status the new status for the project with this title
     * @return the updated {@link Project} entity
     * @throws IllegalArgumentException if no project with the given title is found
     */
    @Override
    public Project updateProjectStatus(String title, String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        Project project = projectRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        project.setStatus(status);
        return projectRepository.save(project);
    }

    /**
     * Retrieves all projects.
     *
     * @return a list of all {@link Project} entities
     */
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Retrieves a project by its title.
     *
     * @param title the title of the project to retrieve
     * @return the {@link Project} entity with the given title
     * @throws IllegalArgumentException if no project with the given title is found
     */
    @Override
    public Project getProjectByTitle(String title) {
        return projectRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Project with the given title not found"));
    }

    /**
     * Deletes a project by its title.
     *
     * @param title the title of the project to delete
     * @throws IllegalArgumentException if no project with the given title is found
     */
    @Override
    public void deleteProjectByTitle(String title) {
        Project project = projectRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Project with the given title not found"));
        projectRepository.delete(project);
    }
}
