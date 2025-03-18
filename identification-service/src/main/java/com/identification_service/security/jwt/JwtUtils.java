package com.identification_service.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.identification_service.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * This class provides methods for generating, parsing, and validating JWT tokens.
 * It is used for authentication and authorization in the application.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${recruitment.app.jwtSecret}")
    private String jwtSecret;

    @Value("${recruitment.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param authentication the authentication object containing user details
     * @return the generated JWT token as a string
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Extrahera roller som en lista av strängar
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Long id = userPrincipal.getId();
        String email = userPrincipal.getEmail();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles) // Lägg till roller som en lista
                .claim("userId", id) // Lägg också till användarens ID om det behövs
                .claim("email", email) // Och andra användaruppgifter som behövs
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username contained in the token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Checks if the token is correctly signed and not expired.
     * Logs errors if the token is invalid.
     *
     * @param authToken the JWT token to validate
     * @return {@code true} if the token is valid, otherwise {@code false}
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}