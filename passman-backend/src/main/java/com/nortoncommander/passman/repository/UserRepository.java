package com.nortoncommander.passman.repository;

import com.nortoncommander.passman.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  User findByUsername(String username);
}
