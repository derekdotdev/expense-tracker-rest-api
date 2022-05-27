package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.User;
import com.derekdileo.expensetrackerrestapi.entity.UserModel;

public interface UserService {
    User createUser(UserModel user);

    // Read information about current user
    User readUserById(Long id);
}
