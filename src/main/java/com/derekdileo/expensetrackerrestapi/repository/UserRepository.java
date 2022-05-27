package com.derekdileo.expensetrackerrestapi.repository;

import com.derekdileo.expensetrackerrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check existence of email address (unique)
    Boolean existsByEmail(String email);
}
