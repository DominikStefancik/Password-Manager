package com.nortoncommander.passman.validator;

import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.model.User;
import com.nortoncommander.passman.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserValidatorTest {

  @MockBean
  private UserService userService;

  @Autowired
  UserValidator userValidator;

  @Test
  public void validate_existing_user_with_username_validates_correctly() {
    final var username = "TestUser";
    Mockito.when(userService.getUserWithUsername(username))
      .thenReturn(User.builder()
        .username(username)
        .build());

    assertThat(userValidator.validateExistingUser(username)).isTrue();
  }

  @Test
  public void validate_required_fields_validates_correctly() {
    final var userDto = UserDTO.builder()
      .name("TestPassword")
      .username("TestUser")
      .email("test@test.com")
      .build();

    var userWithoutName = userDto.builder()
      .name(null)
      .build();

    var userWithoutUsername = userDto.builder()
      .username(null)
      .build();

    var userWithoutEmail = userDto.builder()
      .email(null)
      .build();

    assertThat(userValidator.validateRequiredFields(userDto)).isTrue();
    assertThat(userValidator.validateRequiredFields(userWithoutName)).isFalse();
    assertThat(userValidator.validateRequiredFields(userWithoutUsername)).isFalse();
    assertThat(userValidator.validateRequiredFields(userWithoutEmail)).isFalse();
  }
}
