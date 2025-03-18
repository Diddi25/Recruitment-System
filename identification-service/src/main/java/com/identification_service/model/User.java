package com.identification_service.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a user in the system.
 */
@Entity
@Table( name = "person",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank
    @Size(min = 3, max = 20)
    private String surname;

    @NotBlank
    @Size(min = 3, max = 20)
    private String personNumber;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 2, max = 150)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructs a new user with the specified username, email, and password.
     *
     * @param username the username of the user
     * @param email    the email of the user
     * @param password the password of the user
     */
    public User(String name, String surname, String personNumber, String email, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.personNumber = personNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the unique identifier for this user.
     *
     * @return the personId of the user
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * Sets the unique identifier for this user.
     *
     * @param personId the personId to set for the user
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    /**
     * Gets the username of this user.
     *
     * @return the username of the user
     */
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

    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this user.
     *
     * @param username the username to set for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email of this user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email for this user.
     *
     * @param email the email to set for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of this user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for this user.
     *
     * @param password the password to set for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the roles associated with this user.
     *
     * @return a set of roles assigned to the user
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles for this user.
     *
     * @param roles a set of roles to assign to the user
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
