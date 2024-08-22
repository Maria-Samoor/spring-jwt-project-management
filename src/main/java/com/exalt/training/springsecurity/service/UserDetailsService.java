package com.exalt.training.springsecurity.service;

import com.exalt.training.springsecurity.dto.UserDTO;
import com.exalt.training.springsecurity.model.Role;
import com.exalt.training.springsecurity.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserDetailsService {

    /**
     * Creates a new user based on the provided UserDTO.
     *
     * @param userDTO the data for the new user
     * @return the created user
     */
    User createUser(UserDTO userDTO);

    /**
     * Updates an existing user identified by the email.
     *
     * @param email   the email of the user to be updated
     * @param userDTO the data for the update
     * @return the updated user
     */
    User updateUser(String email, UserDTO userDTO);

    /**
     * Deletes a user identified by the email.
     *
     * @param email the email of the user to be deleted
     */
    void deleteUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    List<User> getAllUsers();

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the user associated with the given email
     */
    User getUserByEmail(String email);

    /**
     * Updates the role of an existing user based on the provided email.
     *
     * @param email the email of the user whose role is being updated
     * @param newRole the new role to assign to the user
     * @return the updated user with the new role
     * @throws UsernameNotFoundException if the user with the specified email is not found
     */
    User updateUserRole(String email, Role newRole);
}
