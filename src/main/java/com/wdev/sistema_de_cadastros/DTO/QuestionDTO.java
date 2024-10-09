package com.wdev.sistema_de_cadastros.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class QuestionDTO {
    @Schema(description = "Texto da Questão", required = true)
    @NotBlank(message = "The question must have a text")
    private String questionText;

    @Schema(hidden = true)
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
