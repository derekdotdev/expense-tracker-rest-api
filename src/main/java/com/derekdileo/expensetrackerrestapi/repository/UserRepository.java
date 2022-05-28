package com.derekdileo.expensetrackerrestapi.repository;

import com.derekdileo.expensetrackerrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check existence of email address (unique)
    Boolean existsByEmail(String email);

    // Query method to find user by email (if exists)
    Optional<User> findByEmail(String email);
}
