package com.exalt.training.springsecurity.exception;

/**
 * Exception thrown when a project title already exists in the system.
 * This custom exception is used to indicate that the project title provided is not unique.
 */
public class ProjectTitleAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new ProjectTitleAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public ProjectTitleAlreadyExistsException(String message) {
        super(message);
    }
}