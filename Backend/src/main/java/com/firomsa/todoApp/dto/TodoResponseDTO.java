package com.firomsa.todoApp.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoResponseDTO {
    private int id;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private UUID userId;
}
