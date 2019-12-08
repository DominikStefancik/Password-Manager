package com.nortoncommander.passman.validator;

import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {

  private UserService userService;

  public UserValidator(UserService userService) {
    this.userService = userService;
  }

  public boolean validateExistingUser(String username) {
    var existingUser = userService.getUserWithUsername(username);

    return existingUser != null;
  }

  public boolean validateRequiredFields(UserDTO userDTO) {
    return userDTO.getName() != null && userDTO.getUsername() != null && userDTO.getEmail() != null;
  }
}
