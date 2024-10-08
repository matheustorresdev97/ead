package com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(Module module) {
        List<Lesson> lessonList = lessonRepository.findAllLessonIntoModule(module.getModuleId());
        if (!lessonList.isEmpty()) {
            lessonRepository.deleteAll(lessonList);
        }
        moduleRepository.delete(module);
    }
}
