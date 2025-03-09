package com.identification_service.controller;

import com.identification_service.exception.ValidationFailedException;
import com.identification_service.model.EnumRoles;
import com.identification_service.model.Role;
import com.identification_service.model.User;
import com.identification_service.payload.request.LoginRequest;
import com.identification_service.payload.request.SignupRequest;
import com.identification_service.payload.response.JwtResponse;
import com.identification_service.payload.response.MessageResponse;
import com.identification_service.repository.RoleRepository;
import com.identification_service.repository.UserRepository;
import com.identification_service.security.jwt.JwtUtils;
import com.identification_service.security.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private SignupRequest signupRequest;
    private User user;
    private Role userRole;
    private Role recruiterRole;
    private UserDetailsImpl userDetails;
    private final String JWT_TOKEN = "test-jwt-token";

    @BeforeEach
    void setUp() {
        // Setup login request
        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        // Setup signup request
        signupRequest = new SignupRequest();
        signupRequest.setName("Test");
        signupRequest.setSurname("User");
        signupRequest.setPersonNumber("12345");
        signupRequest.setEmail("test@example.com");
        signupRequest.setUsername("testuser");
        signupRequest.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        signupRequest.setRole(roles);

        // Setup user
        user = new User();
        user.setName("Test");
        user.setSurname("User");
        user.setPersonNumber("12345");
        user.setEmail("test@example.com");
        user.setUsername("testuser");

        // Setup roles
        userRole = new Role();
        userRole.setId(1);
        userRole.setName(EnumRoles.ROLE_USER);

        recruiterRole = new Role();
        recruiterRole.setId(2);
        recruiterRole.setName(EnumRoles.ROLE_RECRUITER);

        // Setup user details
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(EnumRoles.ROLE_USER.name()));
        userDetails = new UserDetailsImpl(
                1L, "testuser", "Test", "12345", "test@example.com", "testuser", "password123", authorities);
    }

    @Test
    @DisplayName("Should authenticate user and return JWT token")
    void authenticateUser_ShouldReturnJwtResponse() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(JWT_TOKEN);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Act
        ResponseEntity<?> responseEntity = authController.authenticateUser(loginRequest, httpServletResponse);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        JwtResponse jwtResponse = (JwtResponse) responseEntity.getBody();
        assertEquals(JWT_TOKEN, jwtResponse.getAccessToken());
        assertEquals(userDetails.getId(), jwtResponse.getId());
        assertEquals(userDetails.getUsername(), jwtResponse.getUsername());
        assertEquals(userDetails.getEmail(), jwtResponse.getEmail());
        assertEquals(userDetails.getName(), jwtResponse.getName());
        assertEquals(userDetails.getSurname(), jwtResponse.getSurname());
        assertEquals(userDetails.getPersonNumber(), jwtResponse.getPersonNumber());
        assertEquals(roles, jwtResponse.getRoles());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateJwtToken(authentication);
    }

    @Test
    @DisplayName("Should register user with USER role when no role specified")
    void registerUser_WithDefaultRole_ShouldRegisterSuccessfully() {
        // Arrange
        signupRequest.setRole(null);

        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(encoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(EnumRoles.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof MessageResponse);

        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertEquals("User registered successfully!", messageResponse.getMessage());

        verify(userRepository).existsByUsername(signupRequest.getUsername());
        verify(userRepository).existsByEmail(signupRequest.getEmail());
        verify(roleRepository).findByName(EnumRoles.ROLE_USER);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should register user with RECRUITER role when specified")
    void registerUser_WithRecruiterRole_ShouldRegisterSuccessfully() {
        // Arrange
        Set<String> roles = new HashSet<>();
        roles.add("recruiter");
        signupRequest.setRole(roles);

        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(encoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(EnumRoles.ROLE_RECRUITER)).thenReturn(Optional.of(recruiterRole));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof MessageResponse);

        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertEquals("User registered successfully!", messageResponse.getMessage());

        verify(userRepository).existsByUsername(signupRequest.getUsername());
        verify(userRepository).existsByEmail(signupRequest.getEmail());
        verify(encoder).encode(signupRequest.getPassword());
        verify(roleRepository).findByName(EnumRoles.ROLE_RECRUITER);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw ValidationFailedException when email already exists")
    void registerUser_WithExistingEmail_ShouldThrowValidationFailedException() {
        // Arrange
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        // Act & Assert
        ValidationFailedException exception = assertThrows(
                ValidationFailedException.class,
                () -> authController.registerUser(signupRequest)
        );

        assertEquals("Error: Email is already in use!", exception.getMessage());
        verify(userRepository).existsByUsername(signupRequest.getUsername());
        verify(userRepository).existsByEmail(signupRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
    
}