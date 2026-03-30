package com.studyflow.studyflow_backend.controller;

import com.studyflow.studyflow_backend.dto.GoalResponse;
import com.studyflow.studyflow_backend.dto.SubjectResponse;
import com.studyflow.studyflow_backend.entity.Goal;
import com.studyflow.studyflow_backend.entity.Subject;
import com.studyflow.studyflow_backend.entity.User;
import com.studyflow.studyflow_backend.repository.SubjectRepository;
import com.studyflow.studyflow_backend.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.time.LocalDate;
import com.studyflow.studyflow_backend.entity.Goal;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

private void createDefaultDataIfNeeded(String userId) {

    List<Subject> existing = subjectRepository.findAllWithGoalsByUserId(userId);
    if (!existing.isEmpty()) return;

    User user = userRepository.findById(userId)
            .orElseGet(() -> userRepository.save(new User(userId, "", "")));

    Subject math = new Subject("Math", "📐", "blue");
    math.setUser(user);

    Subject science = new Subject("Science", "🔬", "green");
    science.setUser(user);

    Goal g1 = new Goal("Practice 10 problems", false, java.time.LocalDate.now(), math);
    Goal g2 = new Goal("Revise formulas", false, java.time.LocalDate.now(), math);
    Goal g3 = new Goal("Read chapter 1", false, java.time.LocalDate.now(), science);

    math.getGoals().add(g1);
    math.getGoals().add(g2);
    science.getGoals().add(g3);

    subjectRepository.save(math);
    subjectRepository.save(science);
}

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SubjectController(
            SubjectRepository subjectRepository,
            UserRepository userRepository
    ) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<SubjectResponse> getAll(
            @RequestHeader("X-USER-ID") String userId) {

    createDefaultDataIfNeeded(userId); // ✅ ADD THIS LINE

        return subjectRepository
                .findAllWithGoalsByUserId(userId)
                .stream()
                .map(subject -> {

                    List<GoalResponse> goals = subject.getGoals().stream()
                            .map(goal -> new GoalResponse(
                                    goal.getId(),
                                    goal.getTitle(),
                                    goal.isCompleted(),
                                    goal.getDate()
                            ))
                            .toList();

                    return new SubjectResponse(
                            subject.getId(),
                            subject.getName(),
                            subject.getIcon(),
                            subject.getColor(),
                            goals
                    );

                }).toList();
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> create(
            @RequestHeader("X-USER-ID") String userId,
            @RequestBody Subject subject) {

        User user = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(new User(userId, "", "")));

        subject.setUser(user);

        Subject saved = subjectRepository.save(subject);

        SubjectResponse response = new SubjectResponse(
                saved.getId(),
                saved.getName(),
                saved.getIcon(),
                saved.getColor(),
                List.of()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<?> delete(
        @RequestHeader("X-USER-ID") String userId,
        @PathVariable Long id) {

    if (!subjectRepository.existsByIdAndUserId(id, userId)) {
        return ResponseEntity.status(403).build();
    }

    subjectRepository.deleteById(id);
    return ResponseEntity.ok().build();
    }
}