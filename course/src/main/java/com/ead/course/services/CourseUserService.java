package com.ead.course.services;

import java.util.UUID;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;

public interface CourseUserService {
    boolean existsByCourseAndUserId(Course course, UUID userId);

    CourseUser save(CourseUser courseUser);
}
