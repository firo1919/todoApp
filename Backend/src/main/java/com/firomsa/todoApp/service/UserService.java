package com.firomsa.todoApp.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.firomsa.todoApp.dto.RegisterUserRequest;
import com.firomsa.todoApp.dto.ResponseDTO;
import com.firomsa.todoApp.dto.UserResponseDTO;
import com.firomsa.todoApp.exception.ResourceNotFoundException;
import com.firomsa.todoApp.exception.UserNameAlreadyExistsException;
import com.firomsa.todoApp.mapper.UserMapper;
import com.firomsa.todoApp.model.Role;
import com.firomsa.todoApp.model.User;
import com.firomsa.todoApp.repository.RoleRepository;
import com.firomsa.todoApp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
    }

    public ResponseDTO<UserResponseDTO> create(RegisterUserRequest registerUserRequest) {
        if (userRepository.existsByUsername(registerUserRequest.getUsername())) {
            throw new UserNameAlreadyExistsException();
        }

        Role role = roleRepository
                .findByName("USER")
                .orElseThrow(ResourceNotFoundException::new);

        User user = UserMapper.toModel(registerUserRequest);
        LocalDateTime now = LocalDateTime.now();
        user.setRole(role);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        User registeredUser = userRepository.save(user);
        return ResponseDTO.<UserResponseDTO>builder()
                .status(true)
                .message("user registered successfully")
                .data(UserMapper.toDTO(registeredUser))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "USER: " + username + " Not found"));
    }
}
