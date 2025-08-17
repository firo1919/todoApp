package com.firomsa.todoApp.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean active;
    private String role;
}
