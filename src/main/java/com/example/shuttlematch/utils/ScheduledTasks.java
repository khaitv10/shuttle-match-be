package com.example.shuttlematch.utils;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.UserSubscription;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.repository.SubscriptionRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.repository.UserSubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    private final UserRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(cron = "0 0 0 * * *")  // Run every day at 0:00
    //@Scheduled(fixedDelay = 60000)
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

    @Scheduled(cron = "0 0 0 * * ?") // Run at 0:00 do not care what day in week
    @Transactional
    public void deactivateExpiredSubscriptions() {
        // Lấy thời điểm cuối cùng của ngày hiện tại (23:59:59.999999999)
        LocalDateTime endOfToday = LocalDate.now().atStartOfDay().plusDays(1).minusNanos(1);

        List<UserSubscription> expiredSubscriptions = userSubscriptionRepository.findExpiredSubscriptions(endOfToday);

        for (UserSubscription userSubscription : expiredSubscriptions) {
            if (userSubscription.isActive()) {
                User user = userSubscription.getUser();
                user.setDiamondMember(false);
                user.setLikeRemaining(5);

                userSubscription.setStartDate(null);
                userSubscription.setEndDate(null);
                Subscription defaultSubscription = subscriptionRepository.findById(1);
                if (defaultSubscription == null) throw new BusinessException(ResponseCode.SUBSCRIPTION_NOT_FOUND);
                userSubscription.setSubscription(defaultSubscription);

                userRepository.save(user);
                userSubscriptionRepository.save(userSubscription);
            }
        }
    }


}
