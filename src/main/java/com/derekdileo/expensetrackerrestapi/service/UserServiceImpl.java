package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.User;
import com.derekdileo.expensetrackerrestapi.entity.UserModel;
import com.derekdileo.expensetrackerrestapi.exceptions.ItemAlreadyExistsException;
import com.derekdileo.expensetrackerrestapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    // Runtime constructor-based injection of UserRepository dependency
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserModel user) {
        // Validate email is unique
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException("An account already exists with email: " + user.getEmail());
        }

        // Create User entity
        User newUser = new User();

        // copyWith and persist to repo
        BeanUtils.copyProperties(user, newUser);
        return userRepository.save(newUser);
    }
}
