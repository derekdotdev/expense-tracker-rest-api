package com.derekdileo.expensetrackerrestapi.controller;

import com.derekdileo.expensetrackerrestapi.entity.User;
import com.derekdileo.expensetrackerrestapi.entity.UserModel;
import com.derekdileo.expensetrackerrestapi.exceptions.ResourceNotFoundException;
import com.derekdileo.expensetrackerrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    // Runtime constructor-based injection of ExpenseService dependency
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserModel user) {
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<User>(userService.readUserById(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable Long id) {
        return new ResponseEntity<User>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
