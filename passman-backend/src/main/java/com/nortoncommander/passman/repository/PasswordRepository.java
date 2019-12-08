package com.nortoncommander.passman.repository;

import com.nortoncommander.passman.model.Password;
import com.nortoncommander.passman.model.PasswordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PasswordRepository extends JpaRepository<Password, UUID> {

  Password findByNameAndUsername(String name, String username);
  List<Password> findAllPasswordsByUsername(String username);

  @Transactional
  @Modifying
  @Query("UPDATE Password pass SET pass.name = :name, pass.description = :description, pass.password = :appPassword, pass.appUrl = :appUrl, " +
    "pass.lastUpdated = :lastUpdated, pass.passwordType = :passwordType, pass.username = :username " +
    "WHERE pass.id = :id")
  void updatePassword(UUID id, String name, String description, String appPassword, String appUrl, LocalDateTime lastUpdated,
                      PasswordType passwordType, String username);
}
