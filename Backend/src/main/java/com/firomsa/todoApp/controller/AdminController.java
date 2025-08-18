package com.firomsa.todoApp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firomsa.todoApp.dto.ResponseDTO;
import com.firomsa.todoApp.dto.UserResponseDTO;
import com.firomsa.todoApp.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "API accessible for admins")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        ResponseDTO<List<UserResponseDTO>> response = userService.getAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getAdminProfile(Principal principal) {
        String adminUserName = principal.getName();
        ResponseDTO<UserResponseDTO> response = userService.get(adminUserName);
        return ResponseEntity.ok().body(response);
    }

}
