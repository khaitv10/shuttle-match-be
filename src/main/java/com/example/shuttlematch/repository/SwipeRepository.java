package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.Swipe;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.SwipeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {
    List<Swipe> findByToUserIdAndSwipeTypeAndStatus(Long id, SwipeType swipeType, Status status);
    List<Swipe> findByFromUserIdAndSwipeType(Long id, SwipeType swipeType);

    Swipe findByToUserIdAndFromUserIdAndSwipeTypeAndStatus(
            Long fromUserId,
            Long toUserId,
            SwipeType swipeType,
            Status status
    );
}
