package com.project.scm.SmartContactManager.services;

import com.project.scm.SmartContactManager.dao.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(int id);

    Optional<User> updateUser(User user) throws Throwable;

    void deleteUser(int id);

    boolean isUserExist(int id);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();
}
