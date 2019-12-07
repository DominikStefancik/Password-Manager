package com.nortoncommander.passman.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@AllArgsConstructor
@Builder
public class UserDTO {

  private String name;
  private String username;
  private String email;
  private Date dateOfBirth;
}
