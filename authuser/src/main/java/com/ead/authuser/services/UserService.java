package com.ead.authuser.services;

import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(UUID userId);

    void delete(User user);

    void save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
