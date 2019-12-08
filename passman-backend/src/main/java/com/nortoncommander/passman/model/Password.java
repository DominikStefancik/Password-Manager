package com.nortoncommander.passman.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_password")
public class Password {

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "password")
  private String password;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "password_type")
  private PasswordType passwordType;

  @Column(name = "username")
  private String username;

  @Column(name = "app_url")
  private String appUrl;

  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;
}
