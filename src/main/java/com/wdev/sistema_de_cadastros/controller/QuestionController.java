package com.wdev.sistema_de_cadastros.controller;

import com.wdev.sistema_de_cadastros.DTO.QuestionDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping()
    public ResponseEntity getAllQuestions() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("data", questionService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred when create the question"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        try {
            return ResponseEntity.ok(Map.of("message", "Question created successfully!", "questionId", questionService.save(questionDTO).getQuestionId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred when create the question"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable UUID id) {
        try {
            questionService.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "successfully deleted"));
        } catch (CustomizedExceptions e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred when delete the question"));
        }
    }
}
