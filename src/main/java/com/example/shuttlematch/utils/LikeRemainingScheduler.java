package com.example.shuttlematch.utils;

import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeRemainingScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *")  // Run every day at 0:00
    public void resetLikeRemaining() {
        log.info("Starting to reset likeRemaining for all users");
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            if (user.isDiamondMember()) {
                user.setLikeRemaining(10);
            } else {
                user.setLikeRemaining(5);
            }

            userRepository.save(user);
        });
        log.info("Finished resetting likeRemaining for all users");
    }
}
