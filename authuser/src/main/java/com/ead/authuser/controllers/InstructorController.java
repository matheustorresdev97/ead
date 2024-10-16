package com.ead.authuser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.dtos.InstructorDto;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UserService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    UserService userService;

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto) {
        Optional<User> userOptional = userService.findById(instructorDto.getUserId());
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            var user = userOptional.get();
            user.setUserType(UserType.INSTRUCTOR);
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

}
