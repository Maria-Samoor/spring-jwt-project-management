package com.exalt.training.springsecurity.config;
import com.exalt.training.springsecurity.model.Role;
import com.exalt.training.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up security, including JWT filtering, role-based access, and authentication.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    /**
     * Configures the security filter chain, defining the endpoints' access rules,
     * session management, and adding the JWT filter before the username/password filter.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception In case of configuration errors.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer ::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/exalt/training/security/auth/**")
                        .permitAll()
                        .requestMatchers("/exalt/training/projects/create").hasAuthority(Role.CEO.name())
                        .requestMatchers("/exalt/training/projects/update/{title}").hasAuthority(Role.CEO.name())
                        .requestMatchers("/exalt/training/projects/update-status/{title}").hasAnyAuthority(Role.CEO.name(), Role.TeamLeader.name())
                        .requestMatchers("/exalt/training/projects/delete/{title}").hasAuthority(Role.CEO.name())
                        .requestMatchers("/exalt/training/projects/all").hasAnyAuthority(Role.CEO.name(), Role.TeamLeader.name(), Role.TeamMember.name())
                        .requestMatchers("/exalt/training/projects/retrieve/{title}").hasAnyAuthority(Role.CEO.name(), Role.TeamLeader.name(), Role.TeamMember.name())
                        .requestMatchers("/exalt/training/users/all").hasAuthority(Role.CEO.name())
                        .requestMatchers("/exalt/training/users/retrieve/{email}").hasAnyAuthority(Role.CEO.name(), Role.TeamLeader.name())
                        .requestMatchers("/exalt/training/users/create").hasAuthority(Role.CEO.name())
                        .requestMatchers("/exalt/training/users/update/{email}").hasAuthority(Role.CEO.name())
                        .requestMatchers("/exalt/training/users/delete/{email}").hasAuthority(Role.CEO.name())
                        .requestMatchers("/exalt/training/users/update-role/{email}").hasAuthority(Role.CEO.name())
                        .anyRequest().authenticated())

                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );

                return http.build();
    }

    /**
     * Configures the DaoAuthenticationProvider, setting the UserDetailsService and PasswordEncoder.
     *
     * @return The configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Creates a PasswordEncoder bean using BCryptPasswordEncoder.
     *
     * @return The PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the AuthenticationManager using the provided AuthenticationConfiguration.
     *
     * @param config The AuthenticationConfiguration object.
     * @return The AuthenticationManager.
     * @throws Exception In case of errors.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }


}
