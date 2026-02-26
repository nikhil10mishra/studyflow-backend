package com.studyflow.studyflow_backend.controller;

import com.studyflow.studyflow_backend.dto.GoalResponse;
import com.studyflow.studyflow_backend.entity.Goal;
import com.studyflow.studyflow_backend.entity.Subject;
import com.studyflow.studyflow_backend.repository.GoalRepository;
import com.studyflow.studyflow_backend.repository.SubjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:8081")
public class GoalController {

    private final GoalRepository goalRepository;
    private final SubjectRepository subjectRepository;

    public GoalController(GoalRepository goalRepository, SubjectRepository subjectRepository) {
        this.goalRepository = goalRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostMapping("/{subjectId}")
    public ResponseEntity<GoalResponse> create(@PathVariable Long subjectId,
                                               @RequestBody Goal goal) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        goal.setSubject(subject);

        Goal saved = goalRepository.save(goal);

        GoalResponse response = new GoalResponse(
                saved.getId(),
                saved.getTitle(),
                saved.isCompleted(),
                saved.getDate()
        );

        return ResponseEntity.ok(response);
    }

@PutMapping("/{id}/toggle")
public ResponseEntity<?> toggle(@PathVariable Long id) {

    return goalRepository.findById(id)
            .map(goal -> {
                goal.setCompleted(!goal.isCompleted());
                Goal saved = goalRepository.save(goal);

                GoalResponse response = new GoalResponse(
                        saved.getId(),
                        saved.getTitle(),
                        saved.isCompleted(),
                        saved.getDate()
                );

                return ResponseEntity.ok(response);
            })
            .orElseGet(() ->
                    ResponseEntity.notFound().build()
            );
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        goalRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}