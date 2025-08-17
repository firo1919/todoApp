package com.firomsa.todoApp.mapper;

import com.firomsa.todoApp.dto.RegisterUserRequest;
import com.firomsa.todoApp.dto.UserResponseDTO;
import com.firomsa.todoApp.model.User;

public class UserMapper {
    private UserMapper() {
    }

    public static UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .active(user.isActive())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().getName())
                .build();
    }

    public static User toModel(RegisterUserRequest registerUserRequest) {
        return User.builder()
                .username(registerUserRequest.getUsername())
                .firstName(registerUserRequest.getFirstName())
                .lastName(registerUserRequest.getLastName())
                .password(registerUserRequest.getPassword())
                .build();
    }
}
