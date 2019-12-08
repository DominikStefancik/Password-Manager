package com.nortoncommander.passman.repository;

import com.nortoncommander.passman.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PasswordRepository extends JpaRepository<Password, UUID> {

  Password findByNameAndUsername(String name, String username);
  List<Password> findAllPasswordsByUsername(String username);
}
