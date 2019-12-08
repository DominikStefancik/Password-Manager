package com.nortoncommander.passman.mapper;

import com.nortoncommander.passman.dto.PasswordDTO;
import com.nortoncommander.passman.model.Password;
import com.nortoncommander.passman.model.PasswordType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordMapperTest {

  @Test
  public void password_is_mapped_to_passworddto_correctly() {
    final var passwordId = UUID.randomUUID();
    final var password = new Password(passwordId, "TestPassword", "Password for testing purposes", "123456", PasswordType.SOFTWARE,
      "TestUser", "http://localhost:4200", LocalDateTime.of(2019, 12, 7, 3, 15));
    final var passwordDto = PasswordMapper.mapToPasswordDto(password);

    assertThat(passwordDto).isNotNull();
    assertThat(passwordDto.getId()).isEqualTo(passwordId);
    assertThat(passwordDto.getName()).isEqualTo("TestPassword");
    assertThat(passwordDto.getUsername()).isEqualTo("TestUser");
    assertThat(passwordDto.getDescription()).isEqualTo("Password for testing purposes");
    assertThat(passwordDto.getPassword()).isEqualTo("123456");
    assertThat(passwordDto.getUsername()).isEqualTo("TestUser");
    assertThat(passwordDto.getPasswordType()).isEqualTo(PasswordType.SOFTWARE);
    assertThat(passwordDto.getAppUrl()).isEqualTo("http://localhost:4200");
    assertThat(passwordDto.getLastUpdated()).isEqualTo(PasswordMapper.FORMATTER.format(LocalDateTime.of(2019, 12, 7, 3, 15)));
  }

  @Test
  public void passworddto_is_mapped_to_password_correctly() {
    final var passwordId = UUID.randomUUID();
    final var passwordDto = new PasswordDTO(passwordId, "TestPassword", "Password for testing purposes", "123456", PasswordType.SOFTWARE,
      "TestUser", "http://localhost:4200", "2019-12-7T03:15");
    final var password = PasswordMapper.mapToPassword(passwordDto);

    assertThat(password).isNotNull();
    assertThat(password.getId()).isEqualTo(passwordId);
    assertThat(password.getName()).isEqualTo("TestPassword");
    assertThat(password.getUsername()).isEqualTo("TestUser");
    assertThat(password.getDescription()).isEqualTo("Password for testing purposes");
    assertThat(password.getPassword()).isEqualTo("123456");
    assertThat(password.getUsername()).isEqualTo("TestUser");
    assertThat(password.getPasswordType()).isEqualTo(PasswordType.SOFTWARE);
    assertThat(password.getAppUrl()).isEqualTo("http://localhost:4200");
    assertThat(password.getLastUpdated()).isNotNull();
  }
}
