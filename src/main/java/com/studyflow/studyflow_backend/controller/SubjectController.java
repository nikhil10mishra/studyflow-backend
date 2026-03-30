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

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

private void createDefaultDataIfNeeded(String userId) {

    List<Subject> existing = subjectRepository.findAllWithGoalsByUserId(userId);
    if (!existing.isEmpty()) return;

    User user = userRepository.findById(userId)
            .orElseGet(() -> userRepository.save(new User(userId, "", "")));

    // ===================== MATHEMATICS =====================
    Subject math = new Subject("Mathematics", "📐", "blue");
    math.setUser(user);

    math.getGoals().add(new Goal("Complete Linear Algebra Ch. 5", true, LocalDate.now(), math));
    math.getGoals().add(new Goal("Practice integration problems", false, LocalDate.now(), math));
    math.getGoals().add(new Goal("Review probability notes", true, LocalDate.now(), math));
    math.getGoals().add(new Goal("Algorithm problem", true, LocalDate.now(), math));

    // ===================== JAVA =====================
    Subject java = new Subject("Java", "☕", "orange");
    java.setUser(user);

    java.getGoals().add(new Goal("Build REST API with Spring Boot", true, LocalDate.now(), java));
    java.getGoals().add(new Goal("Study design patterns", true, LocalDate.now(), java));
    java.getGoals().add(new Goal("Practice multithreading", false, LocalDate.now(), java));

    // ===================== REACT =====================
    Subject react = new Subject("React", "⚛️", "cyan");
    react.setUser(user);

    react.getGoals().add(new Goal("Learn useReducer patterns", false, LocalDate.now(), react));
    react.getGoals().add(new Goal("Build custom hooks", true, LocalDate.now(), react));

    // ===================== DATA STRUCTURES =====================
    Subject ds = new Subject("Data Structures", "🌳", "green");
    ds.setUser(user);

    ds.getGoals().add(new Goal("Implement AVL trees", false, LocalDate.now(), ds));
    ds.getGoals().add(new Goal("Solve graph traversal problems", false, LocalDate.now(), ds));
    ds.getGoals().add(new Goal("Review hash map internals", true, LocalDate.now(), ds));
    ds.getGoals().add(new Goal("Dynamic programming practice", false, LocalDate.now(), ds));

    // SAVE ALL
    subjectRepository.save(math);
    subjectRepository.save(java);
    subjectRepository.save(react);
    subjectRepository.save(ds);
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