package com.identification_service.payload.response;

import java.util.List;

/**
 * Response object containing the JWT token and user details.
 * <p>
 * This class is used to send the JWT token along with user information (such as id, username, email, and roles)
 * as a response after successful authentication.
 * </p>
 */
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String surname;
    private String personNumber;
    private String email;
    private String username;
    private List<String> roles;


    /**
     * Constructs a new {@link JwtResponse} with the provided access token, user details, and roles.
     *
     * @param accessToken the JWT token to be sent in the response
     * @param id the ID of the authenticated user
     * @param username the username of the authenticated user
     * @param email the email of the authenticated user
     * @param roles the list of roles assigned to the authenticated user
     */
    public JwtResponse(String accessToken, Long id, String name, String surname, List<String> roles,
                       String personNumber, String email, String username) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personNumber = personNumber;
        this.email = email;
        this.username = username;
        this.roles = roles;
    }

    /**
     * Gets the access token.
     *
     * @return the JWT token
     */
    public String getAccessToken() {
        return token;
    }

    /**
     * Sets the access token.
     *
     * @param accessToken the JWT token to set
     */
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    /**
     * Gets the token type, which is "Bearer" by default.
     *
     * @return the token type
     */
    public String getTokenType() {
        return type;
    }

    /**
     * Sets the token type.
     *
     * @param tokenType the token type to set
     */
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    /**
     * Gets the ID of the authenticated user.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the authenticated user.
     *
     * @param id the user ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the email of the authenticated user.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the authenticated user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the username of the authenticated user.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the authenticated user.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the list of roles assigned to the authenticated user.
     *
     * @return the list of user roles
     */
    public List<String> getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }
}