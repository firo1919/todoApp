package com.firomsa.todoApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "admin")
public class AdminConfig {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
