package com.ead.authuser.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(SpecificationTemplate.UserSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) UUID courseId) {
        Page<User> userPage = null;
        if (courseId != null) {
            userPage = userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        } else {
            userPage = userService.findAll(spec, pageable);
        }
        if (!userPage.isEmpty()) {
            for (User user : userPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID userId) {
        Optional<User> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        log.debug("DELETE deleteUser userId received {} ", userId);
        Optional<User> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            userService.delete(userModelOptional.get());
            log.debug("DELETE deleteUser userId deleted {} ", userId);
            log.info("User deleted successfully userId {} ", userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
        log.debug("PUT updateUser userDto received {} ", userDto.toString());
        Optional<User> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            var user = userModelOptional.get();
            user.setUsername(userDto.getUsername());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setCpf(userDto.getCpf());
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(user);
            log.debug("PUT updateUser userModel userId {} ", user.getUserId());
            log.info("User updated successfully userId {} ", user.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserDto.UserView.PasswordPut.class) @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
        log.debug("PUT updatePassword userDto received {} ", userDto.toString());
        Optional<User> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        if (!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
            log.warn("Mismatched old password userId {} ", userDto.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!");
        } else {
            var user = userModelOptional.get();
            user.setPassword(userDto.getOldPassword());
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(user);
            log.debug("PUT updatePassword userModel saved {} ", user.toString());
            log.debug("PUT updatePassword userId saved {} ", user.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
        }
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
            @RequestBody @Validated(UserDto.UserView.ImagePut.class) @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        Optional<User> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            var user = userModelOptional.get();
            user.setImageUrl(userDto.getImageUrl());
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(user);
            log.debug("PUT updateImage userId saved {} ", user.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }
}
