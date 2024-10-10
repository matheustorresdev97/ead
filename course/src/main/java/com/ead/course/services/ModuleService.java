package com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.ead.course.models.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleService {
    void delete(Module module);

    Module save(Module module);

    Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId);

    List<Module> findAllByCourse(UUID courseId);

    Optional<Module> findById(UUID moduleId);

    Page<Module> findAllByCourse(Specification<Module> spec, Pageable pageable);
}
