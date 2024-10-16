package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    Optional<UserSubscription> findByUserId(long user_id);


}
