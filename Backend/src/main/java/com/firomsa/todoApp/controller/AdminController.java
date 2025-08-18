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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firomsa.todoApp.dto.RegisterUserRequest;
import com.firomsa.todoApp.dto.ResponseDTO;
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
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "API accessible for admins")
public class AdminController {
    private final UserService userService;
    private final TodoService todoService;

    public AdminController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @GetMapping("/users")
    @Operation(summary = "For fetching all users")
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        ResponseDTO<List<UserResponseDTO>> response = userService.getAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/users/{userName}")
    @Operation(summary = "For fetching a user")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getUser(@PathVariable String userName) {
        ResponseDTO<UserResponseDTO> response = userService.get(userName);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/users/{userName}")
    @Operation(summary = "For deleting a user")
    public ResponseEntity<Void> deleteUser(@PathVariable String userName,
            @RequestParam(required = false, defaultValue = "false") boolean force) {
        if (force) {
            userService.forceDelete(userName);
        } else {
            userService.delete(userName);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users")
    @Operation(summary = "For creating an admin")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> createAdmin(Principal principal,
            @Valid @RequestBody RegisterUserRequest userRequestDTO) {
        String userName = principal.getName();
        ResponseDTO<UserResponseDTO> response = userService.createAdmin(userRequestDTO, userName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profile")
    @Operation(summary = "For fetching admin profile")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getAdminProfile(Principal principal) {
        String adminUserName = principal.getName();
        ResponseDTO<UserResponseDTO> response = userService.get(adminUserName);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/profile")
    @Operation(summary = "For updating admin profile")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> updateUserProfile(Principal principal,
            @Valid @RequestBody RegisterUserRequest userRequestDTO) {
        String userName = principal.getName();
        ResponseDTO<UserResponseDTO> response = userService.update(userRequestDTO, userName);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/profile")
    @Operation(summary = "For deleting admin profile")
    public ResponseEntity<Void> deleteUserProfile(Principal principal) {
        String userName = principal.getName();
        userService.delete(userName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todos")
    @Operation(summary = "For fetching todos")
    public ResponseEntity<ResponseDTO<List<TodoResponseDTO>>> getTodos() {
        ResponseDTO<List<TodoResponseDTO>> response = todoService.getAll();
        return ResponseEntity.ok().body(response);
    }
}
