package com.nortoncommander.passman.controller;

import com.nortoncommander.passman.MockConfig;
import com.nortoncommander.passman.TestUtils;
import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.mapper.UserMapper;
import com.nortoncommander.passman.repository.UserRepository;
import com.nortoncommander.passman.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MockConfig.class, UserService.class})
@WebMvcTest(controllers = UserController.class)
public class UserControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  private static final String CREATE_USER_URL = "/user";

  @Test
  public void create_user_request_returns_status_ok() throws Exception {
    final var userDto = UserDTO.builder()
                          .name("TestUser")
                          .username("test")
                          .email("test@test.com")
                          .dateOfBirth("1998-06-15")
                          .build();
    final var user = UserMapper.mapToUser(userDto);
    final var postBuilder = post(CREATE_USER_URL)
      .content(TestUtils.getJsonString(userDto))
      .contentType("application/json;charset=UTF-8");

    Mockito.when(userRepository.save(user)).thenReturn(user);

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isOk());
  }

  @Test
  public void create_user_request_with_missing_name_returns_bad_request() throws Exception {
    final var userDto = UserDTO.builder()
      .username("test")
      .email("test@test.com")
      .dateOfBirth("1998-06-15")
      .build();
    final var postBuilder = post(CREATE_USER_URL)
      .content(TestUtils.getJsonString(userDto))
      .contentType("application/json;charset=UTF-8");

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isBadRequest());
  }

  @Test
  public void create_user_request_with_missing_username_returns_bad_request() throws Exception {
    final var userDto = UserDTO.builder()
      .name("TestUser")
      .email("test@test.com")
      .dateOfBirth("1998-06-15")
      .build();
    final var postBuilder = post(CREATE_USER_URL)
      .content(TestUtils.getJsonString(userDto))
      .contentType("application/json;charset=UTF-8");

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isBadRequest());
  }

  @Test
  public void create_user_request_with_missing_email_returns_bad_request() throws Exception {
    final var userDto = UserDTO.builder()
      .name("TestUser")
      .username("test")
      .dateOfBirth("1998-06-15")
      .build();
    final var postBuilder = post(CREATE_USER_URL)
      .content(TestUtils.getJsonString(userDto))
      .contentType("application/json;charset=UTF-8");

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isBadRequest());
  }
}
