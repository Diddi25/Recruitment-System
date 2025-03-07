package com.identification_service.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.identification_service.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Implementation of {@link UserDetails} representing an authenticated user.
 * <p>
 * This class holds user details such as ID, username, email, password,
 * and granted authorities. It is used by Spring Security for authentication and authorization.
 * </p>
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String surname;

    private String personNumber;

    private String email;

    private String username;

    @JsonIgnore
    private String password;


    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructs a new {@code UserDetailsImpl} object.
     *
     * @param id          the user ID
     * @param username    the username
     * @param email       the user's email
     * @param password    the user's password
     * @param authorities the authorities granted to the user
     */
    public UserDetailsImpl(Long id, String name, String surname, String personNumber,
                           String email, String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personNumber = personNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }


    /**
     * Builds a {@code UserDetailsImpl} object from a {@link User} entity.
     *
     * @param user the user entity
     * @return an instance of {@code UserDetailsImpl}
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(),
                user.getName(),
                user.getSurname(),
                user.getPersonNumber(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of {@link GrantedAuthority}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the user's ID.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the user's email.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    public String getName() {return name;}

    public String getSurname() {return surname;}

    public String getPersonNumber() {return personNumber;}

    /**
     * Returns the user's password.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's username.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return {@code true} if the account is not expired, otherwise {@code false}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return {@code true} if the account is not locked, otherwise {@code false}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return {@code true} if the credentials are not expired, otherwise {@code false}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return {@code true} if the user is enabled, otherwise {@code false}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Checks if this user object is equal to another object.
     *
     * @param o the object to compare with
     * @return {@code true} if they are equal, otherwise {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}