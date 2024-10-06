package com.wdev.sistema_de_cadastros.service;

import com.wdev.sistema_de_cadastros.model.QuestionModel;
import com.wdev.sistema_de_cadastros.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private QuestionRepository questionRepository;

    public void DatabaseInitializer(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeQuestions();
    }

    public void initializeQuestions() {
        if (questionRepository.count() == 0) {
            List<String> defaultQuestions = Arrays.asList(
                    "Qual seu nome completo?",
                    "Qual seu email de contato?",
                    "Qual sua idade?",
                    "Qual sua altura?"
            );

            for (String questionText : defaultQuestions) {
                QuestionModel question = new QuestionModel();
                question.setQuestionText(questionText);
                question.setReserved(true);
                questionRepository.save(question);
            }
        }
    }
}

