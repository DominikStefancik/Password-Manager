package com.nortoncommander.passman.controller;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.mapper.PasswordMapper;
import com.nortoncommander.passman.service.PasswordService;
import com.nortoncommander.passman.validator.PasswordValidator;
import com.nortoncommander.passman.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/password")
public class PasswordController {
  private PasswordService passwordService;
  private UserValidator userValidator;
  private PasswordValidator passwordValidator;

  public PasswordController(PasswordService passwordService, UserValidator userValidator, PasswordValidator passwordValidator) {
    this.passwordService = passwordService;
    this.userValidator = userValidator;
    this.passwordValidator = passwordValidator;
  }

  @GetMapping("/all/{username}")
  public ResponseEntity<?> getAllUserPasswords(@PathVariable("username") String username) {
    log.info("PasswordController.getAllUserPasswords accessed.");

    if (!userValidator.validateExistingUser(username)) {
      return ResponseEntity.badRequest().body(
        String.format("User with the username '%s' doesn't exists", username));
    }

    var allUserPasswords = passwordService.getAllUserPasswords(username);
    var allUserPasswordDtos = allUserPasswords.stream()
                                .map(PasswordMapper::mapToPasswordDto)
                                .collect(Collectors.toList());

    log.info("PasswordController.getAllUserPasswords finished.");

    return ResponseEntity.ok(allUserPasswordDtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPassword(@PathVariable("id") UUID passwordId) {
    log.info("PasswordController.getPassword accessed.");

    var password = passwordService.getPassword(passwordId);

    if (password == null) {
      return ResponseEntity.badRequest().body(String.format("Password with the id '%s' doesn't exist.", passwordId));
    }

    log.info("PasswordController.getPassword finished.");

    return ResponseEntity.ok(PasswordMapper.mapToPasswordDto(password));
  }

  @PostMapping
  public ResponseEntity<?> createPassword(@RequestBody PasswordDTO passwordDTO) {
    log.info("PasswordController.createPassword accessed.");
    log.info("Request body: {}", passwordDTO.toString());

    if (!passwordValidator.validateRequiredFields(passwordDTO)) {
      return ResponseEntity.badRequest().body("Fields 'id', 'name', 'password', 'username' must be provided");
    }

    if (!userValidator.validateExistingUser(passwordDTO.getUsername())) {
      return ResponseEntity.badRequest().body(
        String.format("User with the username '%s' doesn't exists", passwordDTO.getUsername()));
    }

    if (passwordValidator.validateExistingPassword(passwordDTO.getName(), passwordDTO.getUsername())) {
      return ResponseEntity.badRequest().body(
        String.format("Password with the name '%s' for the user with the username '%s' already exists", passwordDTO.getName(), passwordDTO.getUsername()));
    }

    passwordService.createPassword(passwordDTO);

    log.info("PasswordController.createPassword finished.");

    return ResponseEntity.ok().build();
  }

  @PutMapping
  public ResponseEntity<?> updatePassword(@RequestBody PasswordDTO passwordDTO) {
    log.info("PasswordController.updatePassword accessed.");
    log.info("Request body: {}", passwordDTO.toString());

    if (!passwordValidator.validateRequiredFields(passwordDTO)) {
      return ResponseEntity.badRequest().body("Fields 'name', 'password', 'username' must be provided");
    }

    if (!userValidator.validateExistingUser(passwordDTO.getUsername())) {
      return ResponseEntity.badRequest().body(
        String.format("User with the username '%s' doesn't exists", passwordDTO.getUsername()));
    }

    passwordService.updatePassword(passwordDTO);

    log.info("PasswordController.updatePassword finished.");

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePassword(@PathVariable("id") UUID passwordId) {
    log.info("PasswordController.deletePassword accessed.");
    log.info("Path variable: {}", passwordId);

    if (!passwordValidator.validateExistingPassword(passwordId)) {
      return ResponseEntity.badRequest().body(String.format("Password with the id '%s' doesn't exist.", passwordId));
    }

    passwordService.deletePassword(passwordId);

    log.info("PasswordController.deletePassword finished.");

    return ResponseEntity.ok().build();
  }
}
