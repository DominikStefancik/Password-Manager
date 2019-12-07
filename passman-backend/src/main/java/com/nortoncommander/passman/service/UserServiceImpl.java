package com.nortoncommander.passman.service;

import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.mapper.UserMapper;
import com.nortoncommander.passman.model.User;
import com.nortoncommander.passman.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User createUser(UserDTO userDTO) {
    var newUser = userRepository.save(UserMapper.mapToUser(userDTO));

    log.info("New user created: " + newUser.toString());
    return newUser;
  }

  @Override
  public User getUserWithUsername(String username) {
    var user = userRepository.findByUsername(username);

    if (user == null) {
      log.info("User with the username '" + username + "' not found.");
    }

    return user;
  }
}
