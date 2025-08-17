package com.firomsa.todoApp.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.firomsa.todoApp.service.JWTAuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTSecurityFilter extends OncePerRequestFilter {

    private JWTAuthService jwtAuthService;
    private UserDetailsService userDetailsService;

    public JWTSecurityFilter(
            JWTAuthService jwtAuthService,
            UserDetailsService userDetailsService) {
        this.jwtAuthService = jwtAuthService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // Reading Token from Authorization Header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String possibleToken = authHeader.substring(7).trim();
            if (!possibleToken.isEmpty()) {
                token = possibleToken;
            }
        }
        if (token != null) {
            String username = jwtAuthService.getSubject(token);
            // if username is not null & Context Authentication must be null
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = userDetailsService.loadUserByUsername(
                        username);
                boolean isValid = jwtAuthService.isValidToken(
                        token,
                        user.getUsername());
                if (isValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            user.getPassword(),
                            user.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));
                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
