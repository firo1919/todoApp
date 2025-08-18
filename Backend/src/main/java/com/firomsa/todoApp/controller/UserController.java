package com.firomsa.todoApp.controller;

import java.security.Principal;

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
@RequestMapping("/api/user")
@Tag(name = "User", description = "API accessible for users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getUserProfile(Principal principal) {
        String userName = principal.getName();
        ResponseDTO<UserResponseDTO> response = userService.get(userName);
        return ResponseEntity.ok().body(response);
    }
}
