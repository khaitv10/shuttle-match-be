package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.Subscription;
import com.example.shuttlematch.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findById(long id);
    Subscription findByName(String name);
    boolean existsByName(String name);

    boolean existsById(Long id);

}
