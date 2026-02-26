package com.studyflow.studyflow_backend.repository;

import com.studyflow.studyflow_backend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT DISTINCT s FROM Subject s LEFT JOIN FETCH s.goals")
    List<Subject> findAllWithGoals();
}