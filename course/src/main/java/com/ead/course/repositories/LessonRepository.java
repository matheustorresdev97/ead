package com.ead.course.repositories;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.course.models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query(value = "select * from lessons where module_module_id = :moduleId", nativeQuery = true)
    List<Lesson> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);

    @Query(value = "select * from lessons where module_module_id = :moduleId and lesson_id = :lessonId", nativeQuery = true)
    Optional<Lesson> findLessonIntoModule(@Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);
}
