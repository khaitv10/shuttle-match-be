package com.example.shuttlematch.repository;

import com.example.shuttlematch.entity.Report;
import com.example.shuttlematch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByReportedBy(User user);
}
