package com.nortoncommander.passman.mapper;

import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.model.User;

public class UserMapper {

  public static UserDTO mapToUserDto(User user) {
    return UserDTO.builder()
      .name(user.getName())
      .username(user.getUsername())
      .email(user.getEmail())
      .dateOfBirth(user.getDateOfBirth())
      .build();
  }

  public static User mapToUser(UserDTO userDTO) {
    return User.builder()
      .name(userDTO.getName())
      .username(userDTO.getUsername())
      .email(userDTO.getEmail())
      .dateOfBirth(userDTO.getDateOfBirth())
      .build();
  }
}
