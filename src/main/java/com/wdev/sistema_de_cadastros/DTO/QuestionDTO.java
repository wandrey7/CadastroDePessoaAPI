package com.wdev.sistema_de_cadastros.DTO;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class QuestionDTO {
    @NotBlank(message = "The question must have a text")
    private String questionText;

    private UUID questionId;

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public @NotBlank(message = "The question must have a text") String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(@NotBlank(message = "The question must have a text") String questionText) {
        this.questionText = questionText;
    }

};
