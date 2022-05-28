package com.derekdileo.expensetrackerrestapi.security;

import com.derekdileo.expensetrackerrestapi.entity.User;
import com.derekdileo.expensetrackerrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Runtime constructor-based injection of UserRepository dependency
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found for the email: " +email));

        // ArrayList will later hold User Authorities
        return new org.springframework.security.core.userdetails.User(
                existingUser.getEmail(), existingUser.getPassword(), new ArrayList<>());
    }

}
