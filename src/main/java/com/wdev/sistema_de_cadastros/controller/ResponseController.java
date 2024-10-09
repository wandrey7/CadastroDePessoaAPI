package com.wdev.sistema_de_cadastros.controller;

import com.wdev.sistema_de_cadastros.DTO.ResponseDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.model.ResponseModel;
import com.wdev.sistema_de_cadastros.repository.ResponseRepository;
import com.wdev.sistema_de_cadastros.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/responses")
@Tag(name = "Respostas")
public class ResponseController {
    @Autowired
    ResponseService responseService;

    @Autowired
    ResponseRepository responseRepository;

    @Operation(summary = "Cria uma resposta para uma questão",
            description = "Cria uma resposta utilizando o ID da pessoa e o ID da questão.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o id da resposta e mensagem de sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"Response successfully registered\", \"responseId\": \"e5cfff79-e1e0-4a70-8a91-319710036036\" }")
                    )),
            @ApiResponse(responseCode = "400", description = "Retorna erro de validação",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Formato de entrada Invalido",
                                            summary = "Exemplo de erro de um formato de entrada inválido",
                                            value = "{\"message\": \"Invalid input format!\"}"
                                    ),
                                    @ExampleObject(
                                            name = "answer em Branco",
                                            summary = "Exemplo de erro de uma resposta em branco",
                                            value = "{\"answer\": \"The answer cannot be blank\"}"
                                    ),
                                    @ExampleObject(
                                            name = "personId em Branco",
                                            summary = "Exemplo de erro de um personId em branco",
                                            value = "{\"personId\": \"The person's ID is mandatory.\"}"
                                    ),
                                    @ExampleObject(
                                            name = "questionId em Branco",
                                            summary = "Exemplo de erro de um questionId em branco",
                                            value = "{\"questionId\": \"The question ID is mandatory.\"}"
                                    )
                            }

                    )),
    })
    @PostMapping
    public ResponseEntity<?> createResponse(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "O corpo da requisição deve incluir o ID da pessoa, o ID da questão e a resposta à questão.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = ResponseDTO.class)
            )) ResponseDTO responseDTO) {
        try {
            ResponseModel save = responseService.save(responseDTO);
            return ResponseEntity.ok(Map.of("responseId", save.getResponseId(),
                    "message", "Response successfully registered"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when creating the response"));
        }
    }

    @Operation(summary = "Deleta uma resposta pelo ID",
            description = "Deleta uma resposta pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso ao deletar",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"successfully deleted response\"}")
                    )),
            @ApiResponse(responseCode = "409", description = "Retorna erro se o ID for inválido",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =
                                    "{\"message\": \"There was an error deleting your id, check that it is correct\"}")
                    )),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResponse(@Valid @PathVariable("id") @Parameter(name = "id",
            description = "ID da resposta a ser deletada", required = true) UUID id) {
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