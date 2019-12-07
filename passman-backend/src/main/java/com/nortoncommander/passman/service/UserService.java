package com.nortoncommander.passman.service;


import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.model.User;

public interface UserService {

  User createUser(UserDTO userDTO);
  User getUserWithUsername(String username);
}
