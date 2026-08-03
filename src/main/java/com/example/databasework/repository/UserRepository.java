package com.example.databasework.repository;

import com.example.databasework.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

    public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByLogin(String login);
    }

