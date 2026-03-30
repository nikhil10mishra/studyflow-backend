package com.studyflow.studyflow_backend.repository;

import com.studyflow.studyflow_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}