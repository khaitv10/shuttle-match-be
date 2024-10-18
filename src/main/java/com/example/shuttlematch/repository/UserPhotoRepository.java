package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {
    Set<UserPhoto> findByUserId(Long id);
}
