package com.nortoncommander.passman;

import com.nortoncommander.passman.repository.PasswordRepository;
import com.nortoncommander.passman.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.nortoncommander.passman")
public class MockConfig {

  @Bean
  public UserRepository userRepository() {
    return Mockito.mock(UserRepository.class);
  }

  @Bean
  public PasswordRepository passwordRepository() {
    return Mockito.mock(PasswordRepository.class);
  }
}
