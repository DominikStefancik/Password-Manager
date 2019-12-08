package com.nortoncommander.passman.service;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.model.Password;

import java.util.List;
import java.util.UUID;

public interface PasswordService {

  void createPassword(PasswordDTO passwordDTO);
  Password getPassword(UUID passwordId);
  Password getPassword(String name, String username);
  List<Password> getAllUserPasswords(String username);
  Password updatePassword(Password password);
  void deletePassword(Password password);
}
