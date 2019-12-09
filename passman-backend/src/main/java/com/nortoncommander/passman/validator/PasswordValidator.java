package com.nortoncommander.passman.validator;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.service.PasswordService;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

  public boolean validateExistingPassword(UUID passwordId) {
    var existingPassword = passwordService.getPassword(passwordId);

    return existingPassword.isPresent();
  }

  public boolean validateRequiredFields(PasswordDTO passwordDTO) {
    return passwordDTO.getId() != null && passwordDTO.getName() != null && passwordDTO.getPassword() != null && passwordDTO.getUsername() != null;
  }
}
