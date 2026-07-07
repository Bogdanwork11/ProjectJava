package com.example.databasework.repository;

import com.example.databasework.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

    public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByLogin(String login);
    }

