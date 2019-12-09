package com.nortoncommander.passman.controller;

import com.nortoncommander.passman.MockConfig;
import com.nortoncommander.passman.model.Password;
import com.nortoncommander.passman.repository.PasswordRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MockConfig.class)
@WebMvcTest(controllers = PasswordController.class)
public class DeletePasswordControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PasswordRepository passwordRepository;

  private static final String DELETE_PASSWORD_URL = "/password/{id}";

  @Test
  public void delete_password_request_returns_status_ok() throws Exception {
    final var id = UUID.randomUUID();
    final var password = Password.builder()
      .id(id)
      .name("TestPassword")
      .password("123456")
      .username("TestUser")
      .lastUpdated(LocalDateTime.now())
      .build();

    final var deleteBuilder = delete(DELETE_PASSWORD_URL, id.toString());

    Mockito.when(passwordRepository.findById(id)).thenReturn(Optional.of(password));

    this.mockMvc.perform(deleteBuilder)
      .andExpect(status().isOk());
  }

  @Test
  public void delete_password_request_with_nonexisting_id_returns_bad_request() throws Exception {
    final var id = UUID.randomUUID();

    final var deleteBuilder = delete(DELETE_PASSWORD_URL, id.toString());

    Mockito.when(passwordRepository.findById(id)).thenReturn(Optional.empty());

    this.mockMvc.perform(deleteBuilder)
      .andExpect(status().isBadRequest());
  }
}
