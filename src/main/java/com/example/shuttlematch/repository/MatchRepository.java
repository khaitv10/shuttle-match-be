package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.Match;
import com.example.shuttlematch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Match findByUser1IdAndUser2IdAndActive(Long user1id, Long user2id, boolean isActive);
    Match findByUser2IdAndUser1IdAndActive(Long user1id, Long user2id, boolean isActive);

    List<Match> findAllByUser1OrUser2(User user1, User user2);

    boolean existsByUser1IdAndUser2Id(Long user1, Long user2);
}
