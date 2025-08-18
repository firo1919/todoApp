package com.firomsa.todoApp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firomsa.todoApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, UUID id);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndIdNot(String username, UUID id);
}
