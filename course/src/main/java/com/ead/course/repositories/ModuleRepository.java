package com.ead.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.course.models.Module;

public interface ModuleRepository extends JpaRepository<Module, UUID> {

    @Query(value = "select * from modules where course_course_id = :courseId", nativeQuery = true)
    List<Module> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "select * from modules where course_course_id and module_id = :moduleId", nativeQuery = true)
    Optional<Module> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("courseId") UUID moduleId);

}
