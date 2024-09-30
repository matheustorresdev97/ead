package com.ead.authuser.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.authuser.models.User;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(UUID userId);
    void delete(User user);
}
