package com.firomsa.todoApp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<List<UserResponseDTO>> getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> usersDTO = users.stream().map(user -> UserMapper.toDTO(user)).toList();
        return ResponseDTO.<List<UserResponseDTO>>builder()
                .status(true)
                .data(usersDTO)
                .message("Users successfully fetched")
                .build();
    }

    public ResponseDTO<UserResponseDTO> get(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "USER: " + username + " Not found"));
        ResponseDTO<UserResponseDTO> data = ResponseDTO.<UserResponseDTO>builder()
                .data(UserMapper.toDTO(user))
                .message("User profile successfully fetched")
                .status(true)
                .build();
        return data;
    }

    public ResponseDTO<UserResponseDTO> update(RegisterUserRequest userRequestDTO, String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "USER: " + username + " Not found"));
        if (userRepository.findByUsernameAndIdNot(userRequestDTO.getUsername(), user.getId()).isPresent()) {
            throw new UserNameAlreadyExistsException();
        }
        user.setUsername(userRequestDTO.getUsername());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return ResponseDTO.<UserResponseDTO>builder()
                .status(true)
                .message("user updated successfully")
                .data(UserMapper.toDTO(savedUser))
                .build();
    }

    public void delete(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "USER: " + username + " Not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO<UserResponseDTO> createAdmin(RegisterUserRequest registerUserRequest) {
        if (userRepository.existsByUsername(registerUserRequest.getUsername())) {
            throw new UserNameAlreadyExistsException();
        }

        Role role = roleRepository
                .findByName("ADMIN")
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
                .message("admin registered successfully")
                .data(UserMapper.toDTO(registeredUser))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void forceDelete(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "USER: " + username + " Not found"));
        userRepository.delete(user);
    }
}
