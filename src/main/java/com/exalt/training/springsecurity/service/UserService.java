package com.exalt.training.springsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Interface representing the service for managing user details.
 */
public interface UserService {

    /**
     * Returns a UserDetailsService to load user-specific data.
     *
     * @return an implementation of UserDetailsService.
     */
    UserDetailsService userDetailsService();
}
