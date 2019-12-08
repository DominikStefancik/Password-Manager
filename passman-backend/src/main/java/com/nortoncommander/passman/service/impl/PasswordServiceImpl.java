package com.nortoncommander.passman.service.impl;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.mapper.PasswordMapper;
import com.nortoncommander.passman.model.Password;
import com.nortoncommander.passman.repository.PasswordRepository;
import com.nortoncommander.passman.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PasswordServiceImpl implements PasswordService {
  private PasswordRepository passwordRepository;

  public PasswordServiceImpl(PasswordRepository passwordRepository) {
    this.passwordRepository = passwordRepository;
  }

  @Override
  public void createPassword(PasswordDTO passwordDTO) {
    final var newPassword = passwordRepository.save(PasswordMapper.mapToPassword(passwordDTO));

    log.info("New password with id: '" + newPassword.getId() + "' created.");
  }

  @Override
  public Password getPassword(UUID passwordId) {
    final var password = passwordRepository.findById(passwordId).orElse(null);

    return password;
  }

  @Override
  public Password getPassword(String name, String username) {
    final var password = passwordRepository.findByNameAndUsername(name, username);

    return password;
  }

  @Override
  public List<Password> getAllUserPasswords(String username) {
    final var allUserPasswords = passwordRepository.findAllPasswordsByUsername(username);

    return allUserPasswords;
  }

  @Override
  public Password updatePassword(Password password) {
    final var updatedPassword = passwordRepository.save(password);

    log.info("Password with id: '" + password.getId() + "' updated.");
    return updatedPassword;
  }

  @Override
  public void deletePassword(Password password) {
    passwordRepository.delete(password);
    log.info("Password with id: '" + password.getId() + "' deleted.");
  }
}
