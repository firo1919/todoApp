package com.firomsa.todoApp.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.firomsa.todoApp.exception.ResourceNotFoundException;
import com.firomsa.todoApp.model.Role;
import com.firomsa.todoApp.model.User;
import com.firomsa.todoApp.repository.RoleRepository;
import com.firomsa.todoApp.repository.UserRepository;

@Component
public class CommandLineAppStartAppRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminConfig adminProperties;

    public CommandLineAppStartAppRunner(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, AdminConfig adminProperties) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminProperties = adminProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsername(adminProperties.getUsername())) {
            LocalDateTime now = LocalDateTime.now();
            Role role = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new ResourceNotFoundException());
            User admin = User.builder()
                    .username(adminProperties.getUsername())
                    .password(passwordEncoder.encode(adminProperties.getPassword()))
                    .firstName(adminProperties.getFirstName())
                    .lastName(adminProperties.getLastName())
                    .role(role)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            userRepository.save(admin);
        }
    }

}
