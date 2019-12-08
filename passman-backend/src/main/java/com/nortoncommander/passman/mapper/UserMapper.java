package com.nortoncommander.passman.mapper;

import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserMapper {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

  public static UserDTO mapToUserDto(User user) {
    return UserDTO.builder()
      .id(user.getId())
      .name(user.getName())
      .username(user.getUsername())
      .email(user.getEmail())
      .dateOfBirth(FORMATTER.format(user.getDateOfBirth()))
      .build();
  }

  public static User mapToUser(UserDTO userDTO) {
    return User.builder()
      .name(userDTO.getName())
      .username(userDTO.getUsername())
      .email(userDTO.getEmail())
      .dateOfBirth(LocalDate.parse(userDTO.getDateOfBirth()))
      .build();
  }
}
