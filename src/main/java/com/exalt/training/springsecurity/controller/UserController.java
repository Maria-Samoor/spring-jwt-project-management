package com.exalt.training.springsecurity.controller;

import com.exalt.training.springsecurity.dto.UserDTO;
import com.exalt.training.springsecurity.model.Role;
import com.exalt.training.springsecurity.model.User;
import com.exalt.training.springsecurity.service.UserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for handling user-related requests.
 */
@RestController
@RequestMapping("/exalt/training/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsService userDetailsService;//UserDetailsService interface used to provide user details service implementation methods
    /**
     * Creates a new user in the system.
     *
     * @param userDTO the data transfer object containing the user's details
     * @param bindingResult the result of validation
     * @return a response entity with the created user or validation error
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CEO')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            return ResponseEntity.ok(userDetailsService.createUser(userDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Updates an existing user based on the provided email.
     *
     * @param email the email of the user to be updated
     * @param userDTO the data transfer object containing the updated user's details
     * @param bindingResult the result of validation
     * @return a response entity with the updated user or validation error
     */
    @PutMapping("/update/{email}")
    @PreAuthorize("hasAuthority('CEO')")
    public ResponseEntity<?> updateUser(@PathVariable String email, @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            return ResponseEntity.ok(userDetailsService.updateUser(email, userDTO));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes a user based on the provided email.
     *
     * @param email the email of the user to be deleted
     * @return a response entity with a success message or error
     */
    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasAuthority('CEO')")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        try {
            userDetailsService.deleteUserByEmail(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves all users from the system.
     *
     * @return a response entity with the list of all users
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('CEO')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userDetailsService.getAllUsers());
    }

    /**
     * Retrieves a user based on the provided email.
     *
     * @param email the email of the user to retrieve
     * @return a response entity with the user details or error
     */
    @GetMapping("/retrieve/{email}")
    @PreAuthorize("hasAuthority('CEO') or hasAuthority('TeamLeader')")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(userDetailsService.getUserByEmail(email));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Updates the role of an existing user based on the provided email.
     *
     * @param email the email of the user whose role is being updated
     * @param newRole the new role to assign to the user
     * @return a response entity with the updated user or error
     */
    @PutMapping("/update-role/{email}")
    @PreAuthorize("hasAuthority('CEO')")
    public ResponseEntity<?> updateUserRole(@PathVariable String email, @RequestParam("role") String newRole) {
        try {
            Role role = Role.valueOf(newRole);
            return ResponseEntity.ok(userDetailsService.updateUserRole(email, role));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
