package com.wdev.sistema_de_cadastros.controller;

import com.wdev.sistema_de_cadastros.DTO.PersonDTO;
import com.wdev.sistema_de_cadastros.DTO.PersonWithResponsesDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.model.PersonModel;
import com.wdev.sistema_de_cadastros.service.PersonMapper;
import com.wdev.sistema_de_cadastros.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @Autowired
    PersonMapper personMapper;

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody @Valid PersonDTO personDTO) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity deletePersonById(@Valid @PathVariable("id") UUID id) {
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

    @GetMapping
    public ResponseEntity<?> getPersonByParamsOrGetAll(
            @Valid @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Integer> age,
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