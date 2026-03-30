package com.studyflow.studyflow_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class GoalResponse {

    private Long id;
    private String title;
    private boolean completed;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public GoalResponse(Long id, String title, boolean completed, LocalDate date) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.date = date;
    }

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
}