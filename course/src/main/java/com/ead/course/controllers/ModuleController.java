package com.ead.course.controllers;

import java.util.List;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.Course;
import com.ead.course.models.Module;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    CourseService courseService;

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid ModuleDto moduleDto) {
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }

        var module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setCourse(courseModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId,
            @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<Module> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }
        moduleService.delete(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully.");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId") UUID courseId,
            @PathVariable(value = "moduleId") UUID moduleId,
            @RequestBody @Valid ModuleDto moduleDto) {
        Optional<Module> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }
        var module = moduleModelOptional.get();
        module.setTitle(moduleDto.getTitle());
        module.setDescription(moduleDto.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(module));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<Module>> getAllModules(@PathVariable(value = "courseId") UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllByCourse(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "courseId") UUID courseId,
            @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<Module> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get());
    }

}
