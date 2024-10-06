package com.wdev.sistema_de_cadastros.service;

import com.wdev.sistema_de_cadastros.DTO.QuestionDTO;
import com.wdev.sistema_de_cadastros.exceptions.CustomizedExceptions;
import com.wdev.sistema_de_cadastros.model.QuestionModel;
import com.wdev.sistema_de_cadastros.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionDTO> findAll() {
        List<QuestionModel> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> {
                    QuestionDTO dto = new QuestionDTO();
                    dto.setQuestionId(question.getQuestionId());
                    dto.setQuestionText(question.getQuestionText());
                    return dto;
                })
                .toList();
    }

    public QuestionModel save(QuestionDTO questionDTO) {
        QuestionModel question = new QuestionModel();
        question.setQuestionText(questionDTO.getQuestionText());
        return questionRepository.save(question);
    }

    public void deleteById(UUID id) throws CustomizedExceptions {
        boolean present = questionRepository.findById(id).isPresent();
        if (idIsReserved(id) || !present) {
            throw CustomizedExceptions.idReservedException("This id cannot be deleted");
        }
        questionRepository.deleteById(id);
    }

    public Optional<QuestionModel> findById(UUID id) {
        return questionRepository.findById(id);
    }

    private boolean idIsReserved(UUID id) {
        return questionRepository.findById(id)
                .map(QuestionModel::isReserved)
                .orElse(false);
    }
}
