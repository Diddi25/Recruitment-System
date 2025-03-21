package com.identification_service.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.identification_service.exception.ValidationFailedException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import com.identification_service.model.EnumRoles;
import com.identification_service.security.services.UserDetailsImpl;
import com.identification_service.model.Role;
import com.identification_service.model.User;
import com.identification_service.payload.request.LoginRequest;
import com.identification_service.payload.request.SignupRequest;
import com.identification_service.payload.response.JwtResponse;
import com.identification_service.payload.response.MessageResponse;
import com.identification_service.repository.RoleRepository;
import com.identification_service.repository.UserRepository;
import com.identification_service.security.jwt.JwtUtils;

/**
 * Controller for handling authentication-related requests, such as user sign-in and sign-up.
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/identification/")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * Authenticates the user based on the provided credentials (username and password).
     *
     * @param loginRequest The login request containing the username and password.
     * @return A response containing the generated JWT token, user details, and roles.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse body = new JwtResponse(jwt, userDetails.getId(), userDetails.getName(),
                userDetails.getSurname(), roles, userDetails.getPersonNumber(),
                userDetails.getEmail(), userDetails.getUsername());

        return ResponseEntity.ok(body);
    }

    /**
     * Registers a new user in the system.
     *
     * @param signUpRequest The sign-up request containing the user's details.
     * @return A response indicating whether the registration was successful or not.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info("start register user");
        log.info(signUpRequest.toString());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ValidationFailedException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ValidationFailedException("Error: Email is already in use!");
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getSurname(),
                signUpRequest.getPersonNumber(), signUpRequest.getEmail(),
                signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(EnumRoles.ROLE_USER)
                    .orElseThrow(() -> new ValidationFailedException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "recruiter":
                        Role recruiterRole = roleRepository.findByName(EnumRoles.ROLE_RECRUITER)
                                .orElseThrow(() -> new ValidationFailedException("Error: Role is not found."));
                        roles.add(recruiterRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(EnumRoles.ROLE_USER)
                                .orElseThrow(() -> new ValidationFailedException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        log.info("registered successfully");
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
