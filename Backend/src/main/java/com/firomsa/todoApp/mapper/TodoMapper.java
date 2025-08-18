package com.firomsa.todoApp.mapper;

import com.firomsa.todoApp.dto.TodoRequestDTO;
import com.firomsa.todoApp.dto.TodoResponseDTO;
import com.firomsa.todoApp.model.Todo;

public class TodoMapper {
    private TodoMapper() {
    }

    public static TodoResponseDTO toDTO(Todo todo) {
        return TodoResponseDTO.builder()
                .id(todo.getId())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .createdAt(todo.getCreatedAt())
                .dueDate(todo.getDueDate())
                .userId(todo.getUser().getId())
                .build();
    }

    public static Todo toModel(TodoRequestDTO todoRequestDTO) {
        return Todo.builder()
                .description(todoRequestDTO.getDescription())
                .dueDate(todoRequestDTO.getDueDate())
                .build();
    }
}
