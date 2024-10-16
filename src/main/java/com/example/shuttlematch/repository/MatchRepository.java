package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Match findByUser1IdAndUser2IdAndActive(Long user1id, Long user2id, boolean isActive);
    Match findByUser2IdAndUser1IdAndActive(Long user1id, Long user2id, boolean isActive);

    boolean existsByUser1IdAndUser2Id(Long user1, Long user2);
}
