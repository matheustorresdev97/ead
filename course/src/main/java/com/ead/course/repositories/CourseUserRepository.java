package com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    boolean existsByCourseAndUser(Course course, UUID userId);
}
