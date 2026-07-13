package com.example.databasework.service;

import com.example.databasework.entity.StatusEntity;
import com.example.databasework.repository.StatusRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadstatus() {

        StatusEntity status1 = new StatusEntity();
        status1.setStatus(true);

        StatusEntity status2 = new StatusEntity();
        status2.setStatus(false);

        statusRepository.save(status1);
        statusRepository.save(status2);
    }
}
