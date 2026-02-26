package com.studyflow.studyflow_backend.dto;

import java.util.List;

public class SubjectResponse {

    private Long id;
    private String name;
    private String icon;
    private String color;
    private List<GoalResponse> goals;

    public SubjectResponse(Long id, String name, String icon, String color, List<GoalResponse> goals) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.goals = goals;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getIcon() { return icon; }
    public String getColor() { return color; }
    public List<GoalResponse> getGoals() { return goals; }
}