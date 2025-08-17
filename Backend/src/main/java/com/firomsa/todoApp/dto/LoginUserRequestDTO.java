package com.firomsa.todoApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUserRequestDTO {
    @NotBlank(message = "UserName is required")
    @Size(max = 100, message = "UserName cannot exceed 100 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 100, message = "Password cannot exceed 100 characters")
    private String password;
}
