package com.nortoncommander.passman.mapper;

import com.nortoncommander.passman.dto.UserDTO;
import com.nortoncommander.passman.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

  @Test
  public void user_is_mapped_to_userdto_correctly() {
    final var user = new User(1L, "Matthew", "matt", "matt@test.com", LocalDate.of(1980, 3, 24));
    final var userDto = UserMapper.mapToUserDto(user);

    assertThat(userDto).isNotNull();
    assertThat(userDto.getId()).isEqualTo(1L);
    assertThat(userDto.getName()).isEqualTo("Matthew");
    assertThat(userDto.getUsername()).isEqualTo("matt");
    assertThat(userDto.getEmail()).isEqualTo("matt@test.com");
    assertThat(userDto.getDateOfBirth()).isEqualTo(LocalDate.of(1980, 3, 24));
  }

  @Test
  public void userdto_is_mapped_to_user_correctly() {
    final var userDto = new UserDTO(1L, "Peter", "pete", "pete@test.com", LocalDate.of(1972, 4, 12));
    final var user = UserMapper.mapToUser(userDto);

    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("Peter");
    assertThat(user.getUsername()).isEqualTo("pete");
    assertThat(user.getEmail()).isEqualTo("pete@test.com");
    assertThat(user.getDateOfBirth()).isEqualTo(LocalDate.of(1972, 4, 12));
  }
}
