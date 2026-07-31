package com.example.databasework.repository;

import com.example.databasework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

    public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
    }

