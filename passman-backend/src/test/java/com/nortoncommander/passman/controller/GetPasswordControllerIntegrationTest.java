package com.nortoncommander.passman.controller;

import com.nortoncommander.passman.MockConfig;
import com.nortoncommander.passman.model.Password;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MockConfig.class)
@WebMvcTest(controllers = PasswordController.class)
public class GetPasswordControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PasswordRepository passwordRepository;

  @MockBean
  private UserService userService;

  private static final String GET_PASSWORD_URL = "/password/{id}";
  private static final String GET_ALL_PASSWORDS_URL = "/password/all/{username}";

  @Test
  public void get_password_request_returns_status_ok() throws Exception {
    final var id = UUID.randomUUID();
    final var password = Password.builder()
      .id(id)
      .name("TestPassword")
      .password("123456")
      .username("TestUser")
      .lastUpdated(LocalDateTime.now())
      .build();

    final var getBuilder = get(GET_PASSWORD_URL, id.toString());

    Mockito.when(passwordRepository.findById(id)).thenReturn(Optional.of(password));

    this.mockMvc.perform(getBuilder)
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.id").value(id.toString()))
      .andExpect(jsonPath("$.name").value("TestPassword"))
      .andExpect(jsonPath("$.password").value("123456"))
      .andExpect(jsonPath("$.username").value("TestUser"))
      .andExpect(jsonPath("$.lastUpdated").isNotEmpty());
  }

  @Test
  public void get_password_request_with_nonexisting_id_returns_bad_request() throws Exception {
    final var id = UUID.randomUUID();

    final var getBuilder = get(GET_PASSWORD_URL, id.toString());

    Mockito.when(passwordRepository.findById(id)).thenReturn(Optional.empty());

    this.mockMvc.perform(getBuilder)
      .andExpect(status().isBadRequest());
  }

  @Test
  public void get_all_passwords_request_returns_status_ok() throws Exception {
    final var id = UUID.randomUUID();
    final var username = "TestUser";
    final var password = Password.builder()
      .id(id)
      .name("TestPassword")
      .password("123456")
      .username("TestUser")
      .lastUpdated(LocalDateTime.now())
      .build();

    final var getBuilder = get(GET_ALL_PASSWORDS_URL, username);

    Mockito.when(passwordRepository.findAllPasswordsByUsername(username)).thenReturn(Arrays.asList(password));
    Mockito.when(userService.getUserWithUsername(username))
      .thenReturn(User.builder().username(username).build());

    this.mockMvc.perform(getBuilder)
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$[0].id").value(id.toString()))
      .andExpect(jsonPath("$[0].name").value("TestPassword"))
      .andExpect(jsonPath("$[0].password").value("123456"))
      .andExpect(jsonPath("$[0].username").value("TestUser"))
      .andExpect(jsonPath("$[0].lastUpdated").isNotEmpty());
  }

  @Test
  public void get_all_passwords_request_with_nonexisting_user_returns_bad_request() throws Exception {
    final var username = "TestUser";

    final var getBuilder = get(GET_ALL_PASSWORDS_URL, username);

    Mockito.when(userService.getUserWithUsername(username)).thenReturn(null);

    this.mockMvc.perform(getBuilder)
      .andExpect(status().isBadRequest());
  }
}
