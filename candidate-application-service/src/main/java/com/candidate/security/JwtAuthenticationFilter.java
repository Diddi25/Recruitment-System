package com.candidate.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${recruitment.app.jwtSecret}")
    private String jwtSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            if (jwt != null && validateJwtToken(jwt)) {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                log.debug("All claims in JWT: {}", claims.entrySet().stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue() + " (" + (entry.getValue() != null ? entry.getValue().getClass() : "null") + ")")
                        .collect(Collectors.joining(", ")));

                String username = claims.getSubject();
                log.info("Extracted username from JWT: {}", username);

                // Hämta roller från JWT-token
                Collection<SimpleGrantedAuthority> authorities = extractAuthorities(claims);
                log.info("Extracted authorities: {}", authorities);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Set Authentication to security context for '{}'", username);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts user roles from JWT token Claims and converts them to SimpleGrantedAuthority objects.
     *
     * @param claims Claims object from decrypted JWT token
     * @return A collection of SimpleGrantedAuthority objects representing the user's roles
     */
    @SuppressWarnings("unchecked")
    private Collection<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        try {
            // Get roles from JWT claims
            Object rolesObject = claims.get("roles");

            // If roles are stored as a list (which is normal)
            if (rolesObject instanceof List<?> rolesList) {
                log.debug("Found roles in token: {}", rolesList);

                // Iterate through the list and add each role as a SimpleGrantedAuthority
                for (Object role : rolesList) {
                    if (role instanceof String) {
                        authorities.add(new SimpleGrantedAuthority((String) role));
                    }
                }
            } else if (rolesObject != null) {
                // If roles exist but not in expected format
                log.warn("Unexpected data type for roles in token: {}", rolesObject.getClass().getName());
            } else {
                // If no roles were found
                log.warn("No roles found in token");
            }
        } catch (Exception e) {
            // Catch and log any errors when extracting roles
            log.error("Error extracting roles from JWT: {}", e.getMessage());
        }

        return authorities;
    }
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        log.debug("Authorization header: {}", headerAuth);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    private boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            log.error("JWT validation error: {}", e.getMessage());
        }

        return false;
    }
}