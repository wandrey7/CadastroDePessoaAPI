package com.wdev.sistema_de_cadastros.controller;

import com.wdev.sistema_de_cadastros.DTO.ResponseDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.model.ResponseModel;
import com.wdev.sistema_de_cadastros.repository.ResponseRepository;
import com.wdev.sistema_de_cadastros.service.ResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/responses")
public class ResponseController {
    @Autowired
    ResponseService responseService;

    @Autowired
    ResponseRepository responseRepository;

    @PostMapping
    public ResponseEntity<?> createResponse(@Valid @RequestBody ResponseDTO responseDTO) {
        try {
            ResponseModel save = responseService.save(responseDTO);
            return ResponseEntity.ok(Map.of("responseId", save.getResponseId(), "message", "Response successfully registered"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when creating the response"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResponse(@Valid @PathVariable("id") UUID id) {
        try {
            responseService.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "successfully deleted response"));
        } catch (CustomizedExceptions e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when delete the response"));
        }
    }
}