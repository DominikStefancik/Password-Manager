package com.nortoncommander.passman.mapper;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.model.Password;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PasswordMapper {

  public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

  public static PasswordDTO mapToPasswordDto(Password password) {
    return PasswordDTO.builder()
      .id(password.getId())
      .name(password.getName())
      .description(password.getDescription())
      .password(password.getPassword())
      .passwordType(password.getPasswordType())
      .username(password.getUsername())
      .appUrl(password.getAppUrl())
      .lastUpdated(FORMATTER.format(password.getLastUpdated()))
      .build();
  }

  public static Password mapToPassword(PasswordDTO passwordDTO) {
    return Password.builder()
      .id(passwordDTO.getId())
      .name(passwordDTO.getName())
      .description(passwordDTO.getDescription())
      .password(passwordDTO.getPassword())
      .passwordType(passwordDTO.getPasswordType())
      .username(passwordDTO.getUsername())
      .appUrl(passwordDTO.getAppUrl())
      .lastUpdated(LocalDateTime.now())
      .build();
  }
}
