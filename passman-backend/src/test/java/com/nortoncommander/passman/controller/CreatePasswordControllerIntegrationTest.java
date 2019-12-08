package com.nortoncommander.passman.controller;

import com.nortoncommander.passman.MockConfig;
import com.nortoncommander.passman.TestUtils;
import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.mapper.PasswordMapper;
import com.nortoncommander.passman.model.User;
import com.nortoncommander.passman.repository.PasswordRepository;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MockConfig.class)
@WebMvcTest(controllers = PasswordController.class)
public class CreatePasswordControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PasswordRepository passwordRepository;

  @MockBean
  private UserService userService;

  private static final String CREATE_PASSWORD_URL = "/password";

  @Test
  public void create_password_request_returns_status_ok() throws Exception {
    final var passwordDto = PasswordDTO.builder()
      .id(UUID.randomUUID())
      .name("TestPassword")
      .password("123456")
      .username("TestUser")
      .build();

    final var password = PasswordMapper.mapToPassword(passwordDto);
    final var postBuilder = post(CREATE_PASSWORD_URL)
      .content(TestUtils.getJsonString(passwordDto))
      .contentType("application/json;charset=UTF-8");

    Mockito.when(passwordRepository.save(password)).thenReturn(password);
    Mockito.when(userService.getUserWithUsername(passwordDto.getUsername()))
      .thenReturn(User.builder().username(passwordDto.getUsername()).build());

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isOk());
  }

  @Test
  public void create_password_request_with_missing_id_returns_bad_request() throws Exception {
    final var passwordDto = PasswordDTO.builder()
      .name("TestPassword")
      .password("123456")
      .username("TestUser")
      .build();

    final var password = PasswordMapper.mapToPassword(passwordDto);
    final var postBuilder = post(CREATE_PASSWORD_URL)
      .content(TestUtils.getJsonString(passwordDto))
      .contentType("application/json;charset=UTF-8");

    Mockito.when(passwordRepository.save(password)).thenReturn(password);

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isBadRequest());
  }

  @Test
  public void create_password_request_with_missing_name_returns_bad_request() throws Exception {
    final var passwordDto = PasswordDTO.builder()
      .id(UUID.randomUUID())
      .password("123456")
      .username("TestUser")
      .build();

    final var password = PasswordMapper.mapToPassword(passwordDto);
    final var postBuilder = post(CREATE_PASSWORD_URL)
      .content(TestUtils.getJsonString(passwordDto))
      .contentType("application/json;charset=UTF-8");

    Mockito.when(passwordRepository.save(password)).thenReturn(password);

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isBadRequest());
  }

  @Test
  public void create_password_request_with_missing_password_returns_bad_request() throws Exception {
    final var passwordDto = PasswordDTO.builder()
      .id(UUID.randomUUID())
      .name("TestPassword")
      .username("TestUser")
      .build();

    final var password = PasswordMapper.mapToPassword(passwordDto);
    final var postBuilder = post(CREATE_PASSWORD_URL)
      .content(TestUtils.getJsonString(passwordDto))
      .contentType("application/json;charset=UTF-8");

    Mockito.when(passwordRepository.save(password)).thenReturn(password);

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isBadRequest());
  }

  @Test
  public void create_password_request_with_missing_username_returns_bad_request() throws Exception {
    final var passwordDto = PasswordDTO.builder()
      .id(UUID.randomUUID())
      .name("TestPassword")
      .password("123456")
      .build();

    final var password = PasswordMapper.mapToPassword(passwordDto);
    final var postBuilder = post(CREATE_PASSWORD_URL)
      .content(TestUtils.getJsonString(passwordDto))
      .contentType("application/json;charset=UTF-8");

    Mockito.when(passwordRepository.save(password)).thenReturn(password);

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isBadRequest());
  }

  @Test
  public void create_password_request_with_nonexisting_user_returns_bad_request() throws Exception {
    final var passwordDto = PasswordDTO.builder()
      .id(UUID.randomUUID())
      .name("TestPassword")
      .password("123456")
      .username("TestUser")
      .build();

    final var password = PasswordMapper.mapToPassword(passwordDto);
    final var postBuilder = post(CREATE_PASSWORD_URL)
      .content(TestUtils.getJsonString(passwordDto))
      .contentType("application/json;charset=UTF-8");

    Mockito.when(passwordRepository.save(password)).thenReturn(password);
    Mockito.when(userService.getUserWithUsername(passwordDto.getUsername())).thenReturn(null);

    this.mockMvc.perform(postBuilder)
      .andExpect(status().isBadRequest());
  }
}
