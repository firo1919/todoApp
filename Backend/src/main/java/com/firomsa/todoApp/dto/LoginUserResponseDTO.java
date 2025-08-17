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
public class LoginUserResponseDTO {
    private UUID id;
    private String username;
    private boolean active;
    private String role;
    private String accessToken;
    private UUID refreshToken;
}
