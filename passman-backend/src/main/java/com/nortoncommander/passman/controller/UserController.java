package com.nortoncommander.passman.controller;

import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.mapper.UserMapper;
import com.nortoncommander.passman.service.UserService;
import com.nortoncommander.passman.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  private UserService userService;
  private UserValidator userValidator;

  public UserController(UserService userService, UserValidator userValidator) {
    this.userService = userService;
    this.userValidator = userValidator;
  }

  @GetMapping("/{username}")
  public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
    log.info("UserController.getAllUsersByName accessed.");

    if (username == null) {
      return ResponseEntity.badRequest().body("Parameter 'username' cannot be empty");
    }

    var user = userService.getUserWithUsername(username);

    if (user == null) {
      return ResponseEntity.badRequest().body(String.format("User with the username '%s' doesn't exist", username));
    }

    log.info("UserController.getAllUsersByName finished.");

    return ResponseEntity.ok(UserMapper.mapToUserDto(user));
  }

  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
    log.info("UserController.createUser accessed.");
    log.info("Request body: " + userDTO.toString());

    if (!userValidator.validateRequiredFields(userDTO)) {
      return ResponseEntity.badRequest().body("Fields 'name', 'username', 'email' must be provided");
    }

    if (userValidator.validateExistingUser(userDTO.getUsername())) {
      return ResponseEntity.badRequest().body(String.format("User with the username '%s' doesn't exist", userDTO.getUsername()));
    }

    userService.createUser(userDTO);

    log.info("UserController.createUser finished.");

    return ResponseEntity.ok().build();
  }
}
