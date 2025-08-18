package com.firomsa.todoApp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoRequestDTO {
    @NotBlank(message = "description is required")
    @Size(max = 1000, message = "description cannot exceed 1000 characters")
    private String description;
    @NotNull
    private LocalDateTime dueDate;

    private boolean completed;

}
