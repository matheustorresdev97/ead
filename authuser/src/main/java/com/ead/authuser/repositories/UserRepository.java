package com.ead.authuser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.authuser.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
