package com.ead.course.services;

import com.ead.course.models.Lesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LessonService {
    Lesson save(Lesson lesson);

    Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(Lesson lesson);

    List<Lesson> findAllByModule(UUID moduleId);

    Page<Lesson> findAllByModule(Specification<Lesson> spec, Pageable pageable);
}
