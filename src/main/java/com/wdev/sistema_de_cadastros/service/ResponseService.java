package com.wdev.sistema_de_cadastros.service;

import com.wdev.sistema_de_cadastros.DTO.ResponseDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.model.PersonModel;
import com.wdev.sistema_de_cadastros.model.QuestionModel;
import com.wdev.sistema_de_cadastros.model.ResponseModel;
import com.wdev.sistema_de_cadastros.repository.PersonRepository;
import com.wdev.sistema_de_cadastros.repository.QuestionRepository;
import com.wdev.sistema_de_cadastros.repository.ResponseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ResponseService {
    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    PersonRepository personRepository;

    public ResponseModel save(ResponseDTO responseDTO) {
        PersonModel person = personRepository.findById(responseDTO.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID provided"));

        QuestionModel question = questionRepository.findById(responseDTO.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found with ID provided"));

        ResponseModel response = new ResponseModel();
        response.setPerson(person);
        response.setQuestion(question);
        response.setAnswer(responseDTO.getAnswer());
        response.setResponseId(responseDTO.getResponseId());

        return responseRepository.save(response);
    }

    public void deleteById(UUID id) throws CustomizedExceptions {
        boolean present = responseRepository.findById(id).isPresent();
        if(!present){
            throw CustomizedExceptions.idNotExistException("There was an error deleting your id, check that it is correct");
        }
        responseRepository.deleteById(id);
    }
}
