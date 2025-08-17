package com.firomsa.todoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.firomsa.todoApp.config.SwaggerConfiguration;

@SpringBootApplication
public class TodoAppApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TodoAppApplication.class);
        application.addListeners(new SwaggerConfiguration());
        application.run(args);
    }

}
