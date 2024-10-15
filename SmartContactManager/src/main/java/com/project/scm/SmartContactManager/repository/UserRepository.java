package com.project.scm.SmartContactManager.repository;

import com.project.scm.SmartContactManager.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);
}
