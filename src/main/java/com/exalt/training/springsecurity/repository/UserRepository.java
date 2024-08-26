package com.exalt.training.springsecurity.repository;

import com.exalt.training.springsecurity.model.Role;
import com.exalt.training.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom queries for {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Finds a user by their email.
     *
     * @param email the email address of the user to find.
     * @return an {@link Optional} containing the found {@link User} if present, or {@link Optional#empty()} if not.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their role.
     *
     * @param role the role of the user to find.
     * @return the {@link User} with the specified role.
     */
    User findByRole(Role role);
}
