package com.nortoncommander.passman.validator;

import com.nortoncommander.passman.service.PasswordService;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidator {

  private PasswordService passwordService;

  public PasswordValidator(PasswordService passwordService) {
    this.passwordService = passwordService;
  }

  public boolean validateExistingPassword(String name, String username) {
    var existingPassword = passwordService.getPassword(name, username);

    return existingPassword != null;
  }
}
