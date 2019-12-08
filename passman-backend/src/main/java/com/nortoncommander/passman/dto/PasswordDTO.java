package com.nortoncommander.passman.dto;

import com.nortoncommander.passman.model.PasswordType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
@Builder
public class PasswordDTO {

  private UUID id;
  private String name;
  private String description;
  private String password;
  private PasswordType passwordType;
  private String username;
  private String appUrl;
  private String lastUpdated;
}
