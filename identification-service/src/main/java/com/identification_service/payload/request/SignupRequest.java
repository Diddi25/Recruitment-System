package com.identification_service.payload.request;

import java.util.Set;
import jakarta.validation.constraints.*;

/**
 * This class is used to capture the necessary information for a user to sign up, such as username,
 * email, password, and roles.
 */
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    /**
     * Gets the username for the signup request.
     *
     * @return the username for the signup request
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the signup request.
     *
     * @param username the username to set for the signup request
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email for the signup request.
     *
     * @return the email for the signup request
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email for the signup request.
     *
     * @param email the email to set for the signup request
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password for the signup request.
     *
     * @return the password for the signup request
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the signup request.
     *
     * @param password the password to set for the signup request
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the set of roles for the signup request.
     *
     * @return the set of roles for the signup request
     */
    public Set<String> getRole() {
        return this.role;
    }

    /**
     * Sets the set of roles for the signup request.
     *
     * @param role the set of roles to set for the signup request
     */
    public void setRole(Set<String> role) {
        this.role = role;
    }
}
