package com.exalt.training.springsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling custom exceptions in the application.
 * This class provides centralized exception handling across all controllers.
 */
public class GlobalExceptionHandler {

    /**
     * Handles EmailAlreadyUsedException by returning a BAD_REQUEST (400) response with the exception message.
     *
     * @param ex the EmailAlreadyUsedException that was thrown.
     * @return a ResponseEntity containing the exception message and a BAD_REQUEST status.
     */
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<?> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ProjectTitleAlreadyExistsException by returning a BAD_REQUEST (400) response with the exception message.
     *
     * @param ex the ProjectTitleAlreadyExistsException that was thrown.
     * @return a ResponseEntity containing the exception message and a BAD_REQUEST status.
     */
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<?> handleProjectTitleAlreadyExists(ProjectTitleAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
