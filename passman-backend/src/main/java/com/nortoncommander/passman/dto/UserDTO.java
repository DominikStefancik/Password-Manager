package com.nortoncommander.passman.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class UserDTO {

  private Long id;
  private String name;
  private String username;
  private String email;
  private String dateOfBirth;
}
