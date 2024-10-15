package com.project.scm.SmartContactManager.services.impl;

import com.project.scm.SmartContactManager.dao.User;
import com.project.scm.SmartContactManager.repository.UserRepository;
import com.project.scm.SmartContactManager.services.UserService;
import com.project.scm.SmartContactManager.utils.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        // Fetch User and update details
        User user2 = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setAbout(user.getAbout());
        user2.setImageUrl(user.getImageUrl());
        user2.setEnabled(user.isEnabled());
        user2.setRole(user.getRole());
        //Save the user in DB
        User saved = userRepository.save(user2);
        return Optional.ofNullable(saved);
    }

    @Override
    public void deleteUser(int id) {
        // Fetch User and update details
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        userRepository.delete(user);
    }

    @Override
    public boolean isUserExist(int id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
