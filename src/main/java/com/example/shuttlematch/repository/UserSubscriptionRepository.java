package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    Optional<UserSubscription> findByUserId(long user_id);

    @Query("SELECT us FROM UserSubscription us WHERE us.endDate < :endOfToday")
    List<UserSubscription> findExpiredSubscriptions(@Param("endOfToday") LocalDateTime endOfToday);

}
