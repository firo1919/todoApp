package com.firomsa.todoApp.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firomsa.todoApp.dto.LoginUserRequestDTO;
import com.firomsa.todoApp.dto.LoginUserResponseDTO;
import com.firomsa.todoApp.dto.RefreshTokenDTO;
import com.firomsa.todoApp.dto.RegisterUserRequest;
import com.firomsa.todoApp.dto.ResponseDTO;
import com.firomsa.todoApp.dto.UserResponseDTO;
import com.firomsa.todoApp.exception.AuthException;
import com.firomsa.todoApp.model.RefreshToken;
import com.firomsa.todoApp.model.User;
import com.firomsa.todoApp.repository.RefreshTokenRepository;
import com.firomsa.todoApp.service.JWTAuthService;
import com.firomsa.todoApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API for performing authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JWTAuthService jwtAuthService;

    private final RefreshTokenRepository refreshTokenRepository;

    private static final int REFRESH_TOKEN_DURATION = 15;

    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JWTAuthService jwtAuthService,
            RefreshTokenRepository refreshTokenRepository) {
        this.userService = userService;

        this.authenticationManager = authenticationManager;

        this.jwtAuthService = jwtAuthService;

        this.refreshTokenRepository = refreshTokenRepository;

    }

    @Operation(summary = "For registering a user")
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> registerUser(
            @Valid @RequestBody RegisterUserRequest registerUserRequest) {
        ResponseDTO<UserResponseDTO> responseDTO = userService.create(registerUserRequest);
        log.info("User registered successfully ID: {}", responseDTO.getData().getId());
        return ResponseEntity.ok().body(responseDTO);
    }

    @Operation(summary = "For signing in a user")
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginUserResponseDTO>> loginUser(
            @Valid @RequestBody LoginUserRequestDTO loginUserDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getUsername(),
                        loginUserDTO.getPassword()));

        // generate access token

        String accessToken = jwtAuthService.generateToken(
                loginUserDTO.getUsername());

        // generate a refresh token

        RefreshToken refreshToken = new RefreshToken();
        LocalDateTime now = LocalDateTime.now();
        User user = (User) auth.getPrincipal();
        refreshToken.setUser(user);
        refreshToken.setCreatedAt(now);
        refreshToken.setExpiresAt(now.plusDays(REFRESH_TOKEN_DURATION));
        refreshTokenRepository.save(refreshToken);

        LoginUserResponseDTO loginCredentials = LoginUserResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getId())
                .username(user.getUsername())
                .active(user.isActive())
                .role(user.getRole().getName())
                .id(user.getId())
                .build();
        ResponseDTO<LoginUserResponseDTO> responseDTO = ResponseDTO.<LoginUserResponseDTO>builder()
                .message("Token generated successfully!")
                .data(loginCredentials)
                .status(true)
                .build();
        log.info("User logged in successfully ID: {}", responseDTO.getData().getId());
        return ResponseEntity.ok().body(responseDTO);
    }

    @Operation(summary = "For refreshing accessToken")
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDTO<LoginUserResponseDTO>> refreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByIdAndExpiresAtAfter(
                        UUID.fromString(refreshTokenDTO.getRefreshToken()),
                        LocalDateTime.now())
                .orElseThrow(() -> new AuthException("Invalid or expired refresh token"));

        String accessToken = jwtAuthService.generateToken(
                refreshToken.getUser().getUsername());
        LoginUserResponseDTO loginCredentials = LoginUserResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getId())
                .username(refreshToken.getUser().getUsername())
                .active(refreshToken.getUser().isActive())
                .role(refreshToken.getUser().getRole().getName())
                .id(refreshToken.getUser().getId())
                .build();
        ResponseDTO<LoginUserResponseDTO> responseDTO = ResponseDTO.<LoginUserResponseDTO>builder()
                .message("Token refreshed successfully!")
                .data(loginCredentials)
                .status(true)
                .build();
        log.info("Access token refreshed successfully UserID: {}", responseDTO.getData().getId());
        return ResponseEntity.ok().body(responseDTO);
    }

    @Operation(summary = "For logging out a user")
    @PostMapping("/logout")
    public ResponseEntity<Void> revokeRefreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        RefreshToken refreshToken = refreshTokenRepository
                .findById(
                        UUID.fromString(refreshTokenDTO.getRefreshToken()))
                .orElseThrow(() -> new AuthException("Couldnt find specified token"));

        refreshTokenRepository.deleteById(refreshToken.getId());
        log.info("User logged out successfully ID: {}", refreshToken.getUser().getId());
        return ResponseEntity.noContent().build();
    }
}
