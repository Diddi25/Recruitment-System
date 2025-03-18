package com.identification_service.security.services;

import com.identification_service.exception.ValidationFailedException;
import com.identification_service.model.User;
import com.identification_service.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for loading user details.
 * <p>
 * This class implements {@link UserDetailsService} and is responsible for retrieving user information
 * from the database and converting it into a {@link UserDetails} object.
 * </p>
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user to be loaded
     * @return a {@link UserDetails} object representing the authenticated user
     * @throws ValidationFailedException if no user is found with the given username
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws ValidationFailedException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ValidationFailedException("User not found with username: " + username));

        return UserDetailsImpl.build(user);
    }

}
