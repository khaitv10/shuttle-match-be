package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Optional<User> findByEmailAndStatus(String email, Status status);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    List<User> findAllByStatus(Status status);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userPhotos WHERE u.id = :id")
    Optional<User> findByIdWithPhotos(@Param("id") Long id);


}
