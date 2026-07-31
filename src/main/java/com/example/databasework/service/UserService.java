package com.example.databasework.service;

import com.example.databasework.entity.User;
import com.example.databasework.repository.UserRepository;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadUsers() {
        User admin = new User();
        admin.setId(1);
        admin.setLogin("Bogdankosyanenko@icloud.com");
        admin.setPassword("28085678");
        admin.setRole("ADMIN");
        admin.setIsActive(true);

        User user = new User();
        user.setId(2);
        user.setLogin("David@gmail.com");
        user.setPassword("12345678");
        user.setRole("USER");
        user.setIsActive(false);


        userRepository.save(admin);
        userRepository.save(user);
    }
}
