package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.User;
import com.derekdileo.expensetrackerrestapi.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User readUser();

    User getLoggedInUser();

    User updateUser(UserModel user);

    void deleteUser();

}
