package com.exalt.training.springsecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

/**
 * Represents a user in the system with personal details and role information.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "users")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name="users_sequence",
            sequenceName="users_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id; //user identification number
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName; //user 1st name
    @Column(name = "second_name", nullable = false, length = 50)
    private String secondName; //user 2nd name
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "password", nullable = false)
    private String password; //user password
    @Column(name = "role", nullable = false)
    private Role role; //role assigned to the user (e.g., CEO, TeamLeader)

    /**
     * Returns the authorities granted to the user based on their role.
     * @return A list containing the granted authority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Returns the email of the user, which is used as the username.
     * @return The email of the user.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired.
     * @return Always returns true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked.
     * @return Always returns true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * @return Always returns true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled (active).
     * @return Always returns true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
