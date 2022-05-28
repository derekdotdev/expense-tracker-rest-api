package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.User;
import com.derekdileo.expensetrackerrestapi.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User readUserById(Long id);

    User updateUser(User user, Long id);

    void deleteUser(Long id);

    User getLoggedInUser();
}
