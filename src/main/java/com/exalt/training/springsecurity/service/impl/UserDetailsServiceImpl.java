package com.exalt.training.springsecurity.service.impl;

import com.exalt.training.springsecurity.dto.UserDTO;
import com.exalt.training.springsecurity.model.Role;
import com.exalt.training.springsecurity.model.User;
import com.exalt.training.springsecurity.repository.UserRepository;
import com.exalt.training.springsecurity.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

/**
 * Implementation of the UserDetailsService interface, providing user-related services.
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * Creates a new user in the system based on the provided user data.
     *
     * @param userDTO the data transfer object containing the user's details (first name, last name, email, password, and role)
     * @return the created user
     */
    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setSecondName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }

    /**
     * Updates an existing user's details based on the provided email and user data.
     * Only updates the password if it's provided and non-blank.
     *
     * @param email the email of the user to be updated
     * @param userDTO the data transfer object containing the updated user's details
     * @return the updated user
     * @throws UsernameNotFoundException if the user with the specified email is not found
     */
    @Override
    public User updateUser(String email, UserDTO userDTO) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setSecondName(userDTO.getLastName());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        existingUser.setRole(userDTO.getRole());
        return userRepository.save(existingUser);
    }

    /**
     * Deletes a user from the system based on the provided email.
     *
     * @param email the email of the user to be deleted
     * @throws UsernameNotFoundException if the user with the specified email is not found
     */
    @Override
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
        userRepository.delete(user);
    }

    /**
     * Retrieves all users from the system.
     *
     * @return a list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user based on the provided email.
     *
     * @param email the email of the user to retrieve
     * @return the user with the specified email
     * @throws UsernameNotFoundException if the user with the specified email is not found
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    /**
     * Updates the role of an existing user based on the provided email.
     *
     * @param email the email of the user whose role is being updated
     * @param newRole the new role to assign to the user
     * @return the updated user with the new role
     * @throws UsernameNotFoundException if the user with the specified email is not found
     */
    @Override
    public User updateUserRole(String email, Role newRole) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
        user.setRole(newRole);
        return userRepository.save(user);
    }
}
