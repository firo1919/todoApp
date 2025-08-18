package com.firomsa.todoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firomsa.todoApp.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

}
