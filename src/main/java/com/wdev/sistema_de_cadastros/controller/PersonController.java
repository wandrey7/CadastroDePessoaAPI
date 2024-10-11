package com.wdev.sistema_de_cadastros.controller;

import com.wdev.sistema_de_cadastros.DTO.PersonDTO;
import com.wdev.sistema_de_cadastros.DTO.PersonWithResponsesDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.model.PersonModel;
import com.wdev.sistema_de_cadastros.service.PersonMapper;
import com.wdev.sistema_de_cadastros.service.PersonService;
import com.wdev.sistema_de_cadastros.utils.ApiExamples;
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

import java.util.*;

@RestController
@RequestMapping("/person")
@Tag(name = "Pessoas")
public class PersonController {
    @Autowired
    PersonService personService;

    @Autowired
    PersonMapper personMapper;

    @GetMapping("/")
    public ResponseEntity helloRoute(){
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Visite a documentação: https://sistema-de-cadastros.fly.dev/doc",
                "github", "https://github.com/wandrey7/CadastroDePessoaAPI"));
    }

    @Operation(summary = "Cria uma Pessoa", description = "Cria uma Pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna dados da pessoa e suas respostas as Questões (se houver)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = ApiExamples.SUCCESS_CREATE_PERSON))),
            @ApiResponse(responseCode = "409",
                    description = "Retorna erro por Email já registrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Email already registered!\"}"))),
            @ApiResponse(responseCode = "400",
                    description = "Retorna erro por dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = ApiExamples.ERROR_INVALID_DATA_PERSON)))
    })
    @PostMapping
    public ResponseEntity<?> createPerson(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "O corpo da requisição deve conter os campos name, email, age e height",
            required = true,
            content = @Content(schema = @Schema(implementation = PersonDTO.class))
    ) PersonDTO personDTO) {
        try {
            PersonModel data = personService.savePerson(personDTO);
            return ResponseEntity.ok(Map.of("data", data, "message", "registration completed"));
        } catch (CustomizedExceptions e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when creating the user"));
        }
    }

    @Operation(summary = "Deleta Pessoa por ID", description = "Deleta Pessoa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso ao deletar",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "Sucesso ao deletar", value = "{\"message\": \"successfully deleted\"}")
                    )),
            @ApiResponse(responseCode = "409", description = "Retorna mensagem de erro ao deletar",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "Erro ao deletar id invalido",
                                    value = "{\"message\": \"There was an error deleting your id, check that it is correct\"}")
                    )),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deletePersonById(@Valid @PathVariable("id") @Parameter(name = "id",
            description = "ID da Pessoa a ser deletada", required = true) UUID id) {
        try {
            personService.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "successfully deleted"));
        } catch (CustomizedExceptions e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when deleting the user"));
        }
    }

    @Operation(summary = "Busca Pessoa por parâmetros ou retorna todos",
            description = "Busca Pessoa por parâmetros ou retorna todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados da busca",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "Sucesso ao deletar", value = "d")
                    )),
    })
    @GetMapping
    public ResponseEntity<?> getPersonByParamsOrGetAll(
            @Valid
            @Parameter(description = "Nome da pessoa para buscar")
            @RequestParam(required = false) Optional<String> name,
            @Parameter(description = "Idade da pessoa para buscar")
            @RequestParam(required = false) Optional<Integer> age,
            @Parameter(description = "E-mail da pessoa para buscar")
            @RequestParam(required = false) Optional<String> email) {
        try {
            SequencedCollection<PersonModel> persons = personService.findPersonByParamsOrGetAll(name, age, email);
            List<PersonWithResponsesDTO> personsWithResponses = persons.stream()
                    .map(PersonMapper::mapPersonToDTO)
                    .toList();
            return ResponseEntity.ok(Map.of("data", personsWithResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred when search the user"));
        }
    }
}