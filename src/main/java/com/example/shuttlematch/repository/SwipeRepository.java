package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {

}
