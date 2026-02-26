package com.studyflow.studyflow_backend.controller;

import com.studyflow.studyflow_backend.dto.GoalResponse;
import com.studyflow.studyflow_backend.dto.SubjectResponse;
import com.studyflow.studyflow_backend.entity.Subject;
import com.studyflow.studyflow_backend.repository.SubjectRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "http://localhost:8081")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
public List<SubjectResponse> getAll() {

    return subjectRepository.findAllWithGoals().stream().map(subject -> {

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

@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable Long id) {

    if (!subjectRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }

    subjectRepository.deleteById(id);
    return ResponseEntity.ok().build();
}

}