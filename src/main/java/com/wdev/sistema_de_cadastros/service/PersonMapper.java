package com.wdev.sistema_de_cadastros.service;

import com.wdev.sistema_de_cadastros.DTO.PersonWithResponsesDTO;
import com.wdev.sistema_de_cadastros.DTO.ResponseDTO;
import com.wdev.sistema_de_cadastros.model.PersonModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {
    public static PersonWithResponsesDTO mapPersonToDTO(PersonModel person) {
        PersonWithResponsesDTO personDTO = new PersonWithResponsesDTO();
        personDTO.setPersonId(person.getPersonId());
        personDTO.setName(person.getName());
        personDTO.setEmail(person.getEmail());
        personDTO.setAge(person.getAge());
        personDTO.setHeight(person.getHeight());

        List<ResponseDTO> responseDTOs = person.getResponses().stream()
                .map(response -> {
                    ResponseDTO dto = new ResponseDTO();
                    dto.setResponseId(response.getResponseId());
                    dto.setQuestionId(response.getQuestion().getQuestionId());
                    dto.setQuestionText(response.getQuestion().getQuestionText());
                    dto.setAnswer(response.getAnswer());
                    dto.setPersonId(person.getPersonId());
                    return dto;
                })
                .collect(Collectors.toList());

        personDTO.setResponses(responseDTOs);
        return personDTO;
    }
}
