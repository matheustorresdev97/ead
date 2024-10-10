package com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ead.course.models.Lesson;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }

    @Override
    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    @Override
    public List<Lesson> findAllByModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }

    @Override
    public Page<Lesson> findAllByModule(Specification<Lesson> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }
}
