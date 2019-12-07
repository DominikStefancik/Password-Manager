package com.nortoncommander.passman.controller;

import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.mapper.UserMapper;
import com.nortoncommander.passman.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{username}")
  public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
    log.info("UserController.getAllUsersByName accessed.");

    if (username == null) {
      return ResponseEntity.badRequest().body("Parameter 'username' cannot be empty");
    }

    var user= userService.getUserWithUsername(username);

    if (user == null) {
      return ResponseEntity.badRequest().body("User with the username '" + username + "' doesn't exist.");
    }

    log.info("UserController.getAllUsersByName finished.");

    return ResponseEntity.ok(UserMapper.mapToUserDto(user));
  }

  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
    log.info("UserController.createUser accessed.");
    log.info("Request body: " + userDTO.toString());

    if (userDTO.getName() == null) {
      return ResponseEntity.badRequest().body("Field 'name' must be provided");
    }

    if (userDTO.getUsername() == null) {
      return ResponseEntity.badRequest().body("Field 'username' must be provided");
    }

    if (userDTO.getEmail() == null) {
      return ResponseEntity.badRequest().body("Field 'email' must be provided");
    }

    if (this.validateExistingUser(userDTO.getUsername())) {
      return ResponseEntity.badRequest().body("User with the username " + userDTO.getUsername() + " already exists");
    }

    userService.createUser(userDTO);

    log.info("UserController.createUser finished.");

    return ResponseEntity.ok().build();
  }

  private boolean validateExistingUser(String username) {
    var existingUser = userService.getUserWithUsername(username);

    return existingUser != null;
  }
}
