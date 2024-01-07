package com.example.application.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Logic to load user details from your data source (e.g., database)
        // Replace this with your implementation to fetch user details by username
        // Example code below assumes fetching UserDetails from database by username

        // For demonstration purposes, creating a UserDetails object with hardcoded values
        // Replace this with your actual user retrieval logic
        if ("user123".equals(username)) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username("user123")
                    .password("$2a$10$DgmeZ6ytCx57Wr2SxfuESOvjHz9ENgQXdMUE0wVUuJgGiaCMNBVvW") // Encrypted password
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}

