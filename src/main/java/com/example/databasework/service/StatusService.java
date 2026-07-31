package com.example.databasework.service;

import com.example.databasework.entity.Status;
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

        Status status1 = new Status();
        status1.setStatus(true);

        Status status2 = new Status();
        status2.setStatus(false);

        statusRepository.save(status1);
        statusRepository.save(status2);
    }
}
