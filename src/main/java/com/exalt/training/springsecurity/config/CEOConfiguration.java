package com.exalt.training.springsecurity.config;

import com.exalt.training.springsecurity.model.Role;
import com.exalt.training.springsecurity.model.User;
import com.exalt.training.springsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class responsible for setting up the CEO account during application startup.
 */
@Configuration
public class CEOConfiguration {

    /**
     * Creates a CommandLineRunner bean to check for the existence of a CEO account
     * and create one if it doesn't exist.
     *
     * @param userRepository The repository for performing CRUD operations on User entities.
     * @return A CommandLineRunner that initializes the CEO account if not already present.
     */
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {

        return args -> {
            User  mariaCEOAccount = userRepository.findByRole(Role.CEO);
            User maria = new User();
            maria.setEmail("maria@gmail.com");
            maria.setFirstName("maria");
            maria.setSecondName("Abu Sammour");
            maria.setRole(Role.CEO);
            maria.setPassword(new BCryptPasswordEncoder().encode("maria1234"));
            userRepository.save(maria);
        };
    }
}
