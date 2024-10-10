package com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.course.models.Module;

public interface ModuleService {
    void delete(Module module);

    Module save(Module module);

    Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId);

    List<Module> findAllByCourse(UUID courseId);

    Optional<Module> findById(UUID moduleId);
}
