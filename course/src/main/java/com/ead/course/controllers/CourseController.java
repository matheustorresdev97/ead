package com.ead.course.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.Course;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.validation.CourseValidator;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    CourseValidator courseValidator;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody CourseDto courseDto, Errors errors) {
        log.debug("POST saveCourse courseDto received {} ", courseDto.toString());
        courseValidator.validate(courseDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        var course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseService.save(course);
        log.debug("POST saveCourse courseId saved {} ", course.getCourseId());
        log.info("Course saved successfully courseId {} ", course.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(404).body("Course not found");
        }
        courseService.delete(courseModelOptional.get());
        return ResponseEntity.status(200).body("Course deleted successfully");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid CourseDto courseDto) {
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(404).body("Course not found");
        }
        var course = courseModelOptional.get();
        BeanUtils.copyProperties(courseDto, course);
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.ok(courseService.save(course));
    }

    @GetMapping
    public ResponseEntity<Page<Course>> getAllCourses(SpecificationTemplate.CourseSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) UUID userId) {

        if (userId != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<Course> courseModelOptional = courseService.findById(courseId);
        if (!courseModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get());
    }
}
