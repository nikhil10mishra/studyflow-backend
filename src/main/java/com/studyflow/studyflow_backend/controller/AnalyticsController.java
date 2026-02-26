package com.studyflow.studyflow_backend.controller;

import com.studyflow.studyflow_backend.entity.Goal;
import com.studyflow.studyflow_backend.repository.GoalRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin
public class AnalyticsController {

    private final GoalRepository goalRepository;

    public AnalyticsController(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @GetMapping("/weekly")
    public Map<String, Object> weekly() {

        List<Goal> goals = goalRepository.findAll();

        int total = goals.size();
        int completed = (int) goals.stream().filter(Goal::isCompleted).count();

        int productivity = total == 0 ? 0 : (completed * 100) / total;

        Map<String, Object> response = new HashMap<>();
        response.put("totalGoals", total);
        response.put("completedGoals", completed);
        response.put("productivityScore", productivity);

        return response;
    }
}