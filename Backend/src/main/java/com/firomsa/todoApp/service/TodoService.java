package com.firomsa.todoApp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firomsa.todoApp.dto.ResponseDTO;
import com.firomsa.todoApp.dto.TodoRequestDTO;
import com.firomsa.todoApp.dto.TodoResponseDTO;
import com.firomsa.todoApp.exception.ResourceNotFoundException;
import com.firomsa.todoApp.mapper.TodoMapper;
import com.firomsa.todoApp.model.Todo;
import com.firomsa.todoApp.model.User;
import com.firomsa.todoApp.repository.TodoRepository;
import com.firomsa.todoApp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public ResponseDTO<TodoResponseDTO> get(int id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        return ResponseDTO.<TodoResponseDTO>builder()
                .data(TodoMapper.toDTO(todo))
                .message("successfully fetched a todo")
                .status(true)
                .build();
    }

    public ResponseDTO<List<TodoResponseDTO>> getAll() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponseDTO> data = todos.stream().map(todo -> TodoMapper.toDTO(todo)).toList();
        return ResponseDTO.<List<TodoResponseDTO>>builder()
                .data(data)
                .message("successfully fetched all todos")
                .status(true)
                .build();
    }

    public ResponseDTO<List<TodoResponseDTO>> getAllUserTodos(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "USER: " + username + " Not found"));
        List<Todo> todos = user.getTodos();
        List<TodoResponseDTO> data = todos.stream().map(todo -> TodoMapper.toDTO(todo)).toList();
        return ResponseDTO.<List<TodoResponseDTO>>builder()
                .data(data)
                .message("successfully fetched all user todos")
                .status(true)
                .build();
    }

    @Transactional
    public ResponseDTO<TodoResponseDTO> create(TodoRequestDTO todoRequestDTO, String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "USER: " + username + " Not found"));
        Todo todo = TodoMapper.toModel(todoRequestDTO);
        LocalDateTime now = LocalDateTime.now();
        todo.setCreatedAt(now);
        todo.setUpdatedAt(now);
        todo.setUser(user);
        Todo newTodo = todoRepository.save(todo);
        return ResponseDTO.<TodoResponseDTO>builder()
                .data(TodoMapper.toDTO(newTodo))
                .message("successfully created a todo")
                .status(true)
                .build();
    }

    @Transactional
    public ResponseDTO<TodoResponseDTO> update(TodoRequestDTO todoRequestDTO, int id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        LocalDateTime now = LocalDateTime.now();
        todo.setUpdatedAt(now);
        todo.setDescription(todoRequestDTO.getDescription());
        todo.setDueDate(todoRequestDTO.getDueDate());
        todo.setCompleted(todoRequestDTO.isCompleted());
        Todo newTodo = todoRepository.save(todo);
        return ResponseDTO.<TodoResponseDTO>builder()
                .data(TodoMapper.toDTO(newTodo))
                .message("successfully updated a todo")
                .status(true)
                .build();
    }

    public ResponseDTO<Object> delete(int id) {
        todoRepository.deleteById(id);
        return ResponseDTO.builder()
                .message("successfully deleted a todo")
                .status(true)
                .build();
    }
}
