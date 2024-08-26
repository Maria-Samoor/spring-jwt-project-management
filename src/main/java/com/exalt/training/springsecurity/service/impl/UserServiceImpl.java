package com.exalt.training.springsecurity.service.impl;

import com.exalt.training.springsecurity.repository.UserRepository;
import com.exalt.training.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Implementation of the UserService interface, providing authentication user-related services.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Provides an anonymous implementation of UserDetailsService that loads user data by username.
     *
     * @return an instance of UserDetailsService.
     */
    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
            }
        };
    }
}
