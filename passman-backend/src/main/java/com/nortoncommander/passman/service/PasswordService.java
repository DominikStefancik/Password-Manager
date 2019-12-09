package com.nortoncommander.passman.service;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.model.Password;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PasswordService {

  void createPassword(PasswordDTO passwordDTO);
  Optional<Password> getPassword(UUID passwordId);
  Password getPassword(String name, String username);
  List<Password> getAllUserPasswords(String username);
  void updatePassword(PasswordDTO passwordDTO);
  void deletePassword(UUID passwordId);
}
