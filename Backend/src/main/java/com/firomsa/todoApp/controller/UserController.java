package com.firomsa.todoApp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firomsa.todoApp.dto.RegisterUserRequest;
import com.firomsa.todoApp.dto.ResponseDTO;
import com.firomsa.todoApp.dto.TodoRequestDTO;
import com.firomsa.todoApp.dto.TodoResponseDTO;
import com.firomsa.todoApp.dto.UserResponseDTO;
import com.firomsa.todoApp.service.TodoService;
import com.firomsa.todoApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/user")
@Tag(name = "User", description = "API accessible for users")
public class UserController {
    private final UserService userService;
    private final TodoService todoService;

    public UserController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @GetMapping("/profile")
    @Operation(summary = "For fetching user profile")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getUserProfile(Principal principal) {
        String userName = principal.getName();
        ResponseDTO<UserResponseDTO> response = userService.get(userName);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/profile")
    @Operation(summary = "For updating user profile")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> updateUserProfile(Principal principal,
            @Valid @RequestBody RegisterUserRequest userRequestDTO) {
        String userName = principal.getName();
        ResponseDTO<UserResponseDTO> response = userService.update(userRequestDTO, userName);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/profile")
    @Operation(summary = "For deleting user profile")
    public ResponseEntity<ResponseDTO<Object>> deleteUserProfile(Principal principal) {
        String userName = principal.getName();
        userService.delete(userName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/todos")
    @Operation(summary = "For creating a todo")
    public ResponseEntity<ResponseDTO<TodoResponseDTO>> createTodo(@Valid @RequestBody TodoRequestDTO todoRequestDTO,
            Principal principal) {
        String userName = principal.getName();
        ResponseDTO<TodoResponseDTO> response = todoService.create(todoRequestDTO, userName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/todos")
    @Operation(summary = "For fetching todos")
    public ResponseEntity<ResponseDTO<List<TodoResponseDTO>>> getTodos(Principal principal) {
        String userName = principal.getName();
        ResponseDTO<List<TodoResponseDTO>> response = todoService.getAllUserTodos(userName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/todos/{id}")
    @Operation(summary = "For fetching a given todo")
    public ResponseEntity<ResponseDTO<TodoResponseDTO>> getTodo(@PathVariable int id, Principal principal) {
        String userName = principal.getName();
        ResponseDTO<TodoResponseDTO> response = todoService.get(id, userName);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/todos/{id}")
    @Operation(summary = "For updating a given todo")
    public ResponseEntity<ResponseDTO<TodoResponseDTO>> updateTodo(@PathVariable int id,
            @Valid @RequestBody TodoRequestDTO todoRequestDTO, Principal principal) {
        String userName = principal.getName();
        ResponseDTO<TodoResponseDTO> response = todoService.update(todoRequestDTO, id, userName);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/todos/{id}")
    @Operation(summary = "For removing a given todo")
    public ResponseEntity<Void> deleteTodo(@PathVariable int id, Principal principal) {
        String userName = principal.getName();
        todoService.delete(id, userName);
        return ResponseEntity.noContent().build();
    }
}
