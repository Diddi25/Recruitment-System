package com.identification_service.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * This class captures the necessary information for a user to log in, such as username and password.
 * The fields are validated using Jakarta Bean Validation annotations.
 */
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    /**
     * Gets the username for the login request.
     *
     * @return the username for the login request
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the login request.
     *
     * @param username the username to set for the login request
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password for the login request.
     *
     * @return the password for the login request
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the login request.
     *
     * @param password the password to set for the login request
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
