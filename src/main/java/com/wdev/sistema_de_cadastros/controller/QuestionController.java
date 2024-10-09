package com.wdev.sistema_de_cadastros.controller;

import com.wdev.sistema_de_cadastros.DTO.QuestionDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.service.QuestionService;
import com.wdev.sistema_de_cadastros.utils.ApiExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
@Tag(name = "Questões")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Operation(summary = "Busca todas as Questões",
            description = "Busca todas as Questões")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todas as Questões",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = ApiExamples.RETURN_DATA_QUESTION)
                    )),
    })
    @GetMapping()
    public ResponseEntity getAllQuestions() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("data", questionService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when create the question"));
        }
    }

    @Operation(summary = "Cria uma Questão",
            description = "Cria uma Questão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso ao criar",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"questionId\": \"d1d52c5f-47da-4be8-856a-8b9bb7f8ed80\",\n" +
                                    "\t\"message\": \"Question created successfully!\"}")
                    )),
            @ApiResponse(responseCode = "400", description = "Retorna mensagem de erro criar resposta sem questionText",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"questionText\": \"The question must have a text\"}")
                    )),
    })
    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "O corpo da requisição deve incluir o texto da questão",
            required = true,
            content = @Content(schema = @Schema(implementation = QuestionDTO.class))
    ) QuestionDTO questionDTO) {
        try {
            return ResponseEntity.ok(Map.of("message", "Question created successfully!",
                    "questionId", questionService.save(questionDTO).getQuestionId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when create the question"));
        }
    }

    @Operation(summary = "Deleta Questão por ID", description = "Deleta Questão por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso ao deletar",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "Sucesso ao deletar",
                                    value = "{\"message\": \"successfully deleted\"}")
                    )),
            @ApiResponse(responseCode = "409", description = "Retorna mensagem de erro ao deletar",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "Erro ao deletar",
                                    value = "{\"message\": \"This id cannot be deleted\"}")
                    )),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable @Parameter(name = "id",
            description = "ID da Questão a ser deletada", required = true) UUID id) {
        try {
            questionService.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "successfully deleted"));
        } catch (CustomizedExceptions e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when delete the question"));
        }
    }
}
