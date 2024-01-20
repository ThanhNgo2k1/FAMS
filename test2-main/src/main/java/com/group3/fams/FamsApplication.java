package com.group3.fams;

import com.group3.fams.entity.User;
import com.group3.fams.repositories.UserPermissionRepository;
import com.group3.fams.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class FamsApplication implements CommandLineRunner {

  final UserRepository userRepository;

  final UserPermissionRepository userPermissionRepository;

  final PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(FamsApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (userRepository.findAll().isEmpty()) {
      User user = new User();
      user.setEmail("super_admin@gmail.com");
      user.setPassword(passwordEncoder.encode("superadmin"));
      user.setUserPermission(userPermissionRepository.findById(1).get());
      userRepository.save(user);
      User user2 = new User();
      user2.setEmail("admin@gmail.com");
      user2.setPassword(passwordEncoder.encode("admin"));
      user2.setUserPermission(userPermissionRepository.findById(2).get());
      userRepository.save(user2);
      User user3 = new User();
      user3.setEmail("trainer@gmail.com");
      user3.setPassword(passwordEncoder.encode("trainer"));
      user3.setUserPermission(userPermissionRepository.findById(3).get());
      userRepository.save(user3);
      User user4 = new User();
      user4.setEmail("trainee@gmail.com");
      user4.setPassword(passwordEncoder.encode("trainee"));
      user4.setUserPermission(userPermissionRepository.findById(4).get());
      userRepository.save(user4);
    }
  }
}
