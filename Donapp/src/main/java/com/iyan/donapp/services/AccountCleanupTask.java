package com.iyan.donapp.services;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iyan.donapp.model.User;
import com.iyan.donapp.repositories.UserRepository;

@Component
public class AccountCleanupTask {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    //@Scheduled(fixedRate = 5 * 60 * 1000)
    public void cleanupUnverifiedAccounts() {
    	System.out.println("Ejecutando scheduledjob");
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);
        List<User> unverifiedAccounts = userRepository.findByCreatedDateBeforeAndVerifiedFalse(dateTime);
        userRepository.deleteAll(unverifiedAccounts);
    }
}
