package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.User;
import com.derekdileo.expensetrackerrestapi.entity.UserModel;
import com.derekdileo.expensetrackerrestapi.exceptions.ItemAlreadyExistsException;
import com.derekdileo.expensetrackerrestapi.exceptions.ResourceNotFoundException;
import com.derekdileo.expensetrackerrestapi.repository.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    // Runtime constructor-based injection of UserRepository & BcryptEncoder dependencies
    private final UserRepository userRepo;
    private final PasswordEncoder bcryptEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder bcryptEncoder) {
        this.userRepo = userRepo;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public User createUser(UserModel user) {
        // Validate email is unique
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException(
                    "An account already exists with email: " + user.getEmail());
        }

        // Create User entity
        User newUser = new User();

        // copyWith
        BeanUtils.copyProperties(user, newUser);

        // Encrypt password
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));

        // Persist to repo
        return userRepo.save(newUser);
    }
    @Override
    public User readUser() {
        // findById returns Optional. Use orElseThrow method to handle user not found
        Long userId = getLoggedInUser().getId();
        return userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found for the id: " + userId));
    }

    @Override
    public User updateUser(UserModel user) {
        // Get existing user object for comparison
        User existingUser = readUser();

        // Set fields to existing user object (only if new information exists)
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());

        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());

        existingUser.setPassword(user.getPassword() != null ?
                bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());

        existingUser.setDob(user.getDob() != null ? user.getDob() : existingUser.getDob());

        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser() {
        User existingUser = readUser();
        userRepo.delete(existingUser);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepo.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found for email: " + email));
    }

}
