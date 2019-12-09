package com.nortoncommander.passman.validator;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.model.Password;
import com.nortoncommander.passman.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PasswordValidatorTest {

  @MockBean
  private PasswordService passwordService;

  @Autowired
  PasswordValidator passwordValidator;

  @Test
  public void validate_existing_password_with_name_and_username_validates_correctly() {
    final var passwordName = "TestPassword";
    final var username = "TestUser";
    Mockito.when(passwordService.getPassword(passwordName, username))
      .thenReturn(Password.builder()
        .name(passwordName)
        .username(username)
        .build());

    assertThat(passwordValidator.validateExistingPassword(passwordName, username)).isTrue();
  }

  @Test
  public void validate_existing_password_with_id_validates_correctly() {
    final var passwordId = UUID.randomUUID();
    final var password = Password.builder()
      .id(passwordId)
      .build();
    Mockito.when(passwordService.getPassword(passwordId)).thenReturn(Optional.of(password));

    assertThat(passwordValidator.validateExistingPassword(passwordId)).isTrue();
  }

  @Test
  public void validate_required_fields_validates_correctly() {
    final var passwordDto = PasswordDTO.builder()
      .id(UUID.randomUUID())
      .name("TestPassword")
      .password("123456")
      .username("TestUser")
      .build();

    var passwordWithoutId = passwordDto.builder()
      .id(null)
      .build();

    var passwordWithoutName = passwordDto.builder()
      .name(null)
      .build();

    var passwordWithoutPassword = passwordDto.builder()
      .password(null)
      .build();

    var passwordWithoutUsername = passwordDto.builder()
      .username(null)
      .build();

    assertThat(passwordValidator.validateRequiredFields(passwordDto)).isTrue();
    assertThat(passwordValidator.validateRequiredFields(passwordWithoutId)).isFalse();
    assertThat(passwordValidator.validateRequiredFields(passwordWithoutName)).isFalse();
    assertThat(passwordValidator.validateRequiredFields(passwordWithoutPassword)).isFalse();
    assertThat(passwordValidator.validateRequiredFields(passwordWithoutUsername)).isFalse();
  }
}
