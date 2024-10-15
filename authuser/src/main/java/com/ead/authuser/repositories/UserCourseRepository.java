package com.ead.authuser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.authuser.models.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID>{
    
    boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
}
