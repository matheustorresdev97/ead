package com.ead.course.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseUserRepository courseUserRepository;

    @Override
    public boolean existsByCourseAndUserId(Course course, UUID userId) {
        return courseUserRepository.existsByCourseAndUser(course, userId);
    }

    @Override
    public CourseUser save(CourseUser courseUser) {
        return courseUserRepository.save(courseUser);
    }
}
