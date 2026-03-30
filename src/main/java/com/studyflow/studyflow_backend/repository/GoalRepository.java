package com.studyflow.studyflow_backend.repository;

import com.studyflow.studyflow_backend.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByDate(LocalDate date);
}