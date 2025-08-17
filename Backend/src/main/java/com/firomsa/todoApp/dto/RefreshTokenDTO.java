package com.firomsa.todoApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshTokenDTO {
    @NotBlank(message = "Refresh Token is required")
    private String refreshToken;
}
