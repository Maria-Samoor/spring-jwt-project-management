package com.exalt.training.springsecurity.controller;

import com.exalt.training.springsecurity.dto.ProjectDTO;
import com.exalt.training.springsecurity.exception.ProjectTitleAlreadyExistsException;
import com.exalt.training.springsecurity.model.Project;
import com.exalt.training.springsecurity.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling project-related HTTP requests.
 * <p>
 * Provides endpoints for creating, updating, retrieving, and deleting projects based on their title.
 * </p>
 */
@RestController
@RequestMapping("/exalt/training/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService; //projectService interface to provide project services implementation

    /**
     * Creates a new project.
     *
     * @param projectDTO the data transfer object containing the project details
     * @return ResponseEntity containing the created {@link Project} entity
     */
    @PostMapping("/create")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        try{
            return ResponseEntity.ok(projectService.createProject(projectDTO));
        } catch (ProjectTitleAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Updates an existing project based on its title.
     *
     * @param title the title of the project to be updated
     * @param projectDTO the data transfer object containing the updated project details
     * @return ResponseEntity containing the updated {@link Project} entity
     * @throws IllegalArgumentException if no project with the given title is found
     */
    @PutMapping("/update/{title}")
    public ResponseEntity<?> updateProject(
            @PathVariable("title") String title,
            @Valid @RequestBody ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        try{
            return ResponseEntity.ok(projectService.updateProject(title, projectDTO));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (ProjectTitleAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Updates the status of an existing project based on its title.
     *
     * @param title the title of the project whose status is to be updated
     * @param status the new status
     * @return ResponseEntity containing the updated {@link Project} entity
     * @throws IllegalArgumentException if no project with the given title is found
     */
    @PatchMapping("/update-status/{title}")
    public ResponseEntity<?> updateProjectStatus(
            @PathVariable("title") String title,
            @RequestParam("status") String status) {
        try {
            return ResponseEntity.ok(projectService.updateProjectStatus(title, status));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Retrieves all projects.
     *
     * @return ResponseEntity containing a list of all {@link Project} entities
     */
    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
         return ResponseEntity.ok(projectService.getAllProjects());
    }

    /**
     * Retrieves a project by its title.
     *
     * @param title the title of the project to retrieve
     * @return ResponseEntity containing the {@link Project} entity with the given title
     * @throws IllegalArgumentException if no project with the given title is found
     */
    @GetMapping("/retrieve/{title}")
    public ResponseEntity<?> getProjectByTitle(@PathVariable("title") String title) {
        try {
            return ResponseEntity.ok(projectService.getProjectByTitle(title));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes a project by its title.
     *
     * @param title the title of the project to delete
     * @return ResponseEntity with HTTP status indicating the result of the operation
     * @throws IllegalArgumentException if no project with the given title is found
     */
    @DeleteMapping("/delete/{title}")
    public ResponseEntity<?> deleteProjectByTitle(@PathVariable("title") String title) {
        try {
            projectService.deleteProjectByTitle(title);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}