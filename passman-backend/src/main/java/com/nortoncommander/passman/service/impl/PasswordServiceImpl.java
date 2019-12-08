package com.nortoncommander.passman.service.impl;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.mapper.PasswordMapper;
import com.nortoncommander.passman.model.Password;
import com.nortoncommander.passman.repository.PasswordRepository;
import com.nortoncommander.passman.service.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    log.info("New password with id: '{}' created.", newPassword.getId());
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
  public void updatePassword(PasswordDTO passwordDTO) {
    passwordRepository.updatePassword(passwordDTO.getId(), passwordDTO.getName(), passwordDTO.getDescription(), passwordDTO.getPassword(),
      passwordDTO.getAppUrl(), LocalDateTime.now(), passwordDTO.getPasswordType(), passwordDTO.getUsername());

    log.info("Password with id '{}' updated.", passwordDTO.getId());
  }

  @Override
  public void deletePassword(UUID passwordId) {
    passwordRepository.delete(Password.builder().id(passwordId).build());
    log.info("Password with id '{}' deleted.", passwordId);
  }
}
