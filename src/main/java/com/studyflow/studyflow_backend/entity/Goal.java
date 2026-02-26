package com.studyflow.studyflow_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean completed;

    private LocalDate date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // Constructors
    public Goal() {
    }

    public Goal(String title, boolean completed, LocalDate date, Subject subject) {
        this.title = title;
        this.completed = completed;
        this.date = date;
        this.subject = subject;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDate getDate() {
        return date;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}