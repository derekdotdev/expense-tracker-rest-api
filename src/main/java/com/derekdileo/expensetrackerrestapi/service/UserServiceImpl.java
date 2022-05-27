package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.User;
import com.derekdileo.expensetrackerrestapi.entity.UserModel;
import com.derekdileo.expensetrackerrestapi.exceptions.ItemAlreadyExistsException;
import com.derekdileo.expensetrackerrestapi.exceptions.ResourceNotFoundException;
import com.derekdileo.expensetrackerrestapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    // Runtime constructor-based injection of UserRepository dependency
    private final UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(UserModel user) {
        // Validate email is unique
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException("An account already exists with email: " + user.getEmail());
        }

        // Create User entity
        User newUser = new User();

        // copyWith and persist to repo
        BeanUtils.copyProperties(user, newUser);

        return userRepo.save(newUser);
    }

    @Override
    public User readUserById(Long id) {
        // findById returns Optional. Use orElseThrow method to handle user not found
        return userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found for the id: " + id));
    }

    @Override
    public User updateUser(User user, Long id) {
        // Get existing user object for comparison
        User existingUser = readUserById(id);

        // Set fields to existing user object (only if new information exists)
        existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());

        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());

        existingUser.setPassword(user.getPassword() != null ? user.getPassword() : existingUser.getPassword());

        existingUser.setDob(user.getDob() != null ? user.getDob() : existingUser.getDob());

        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = readUserById(id);
        userRepo.delete(existingUser);
    }

}
