package com.wdev.sistema_de_cadastros.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class ResponseDTO {
    @Schema(description = "Id da questao", required = true)
    @NotNull(message = "The question ID is mandatory.")
    private UUID questionId;

    @Schema(hidden = true)
    private UUID responseId;

    @Schema(description = "Id da pessoa", required = true)
    @NotNull(message = "The person's ID is mandatory.")
    private UUID personId;

    @Schema(hidden = true)
    private String questionText;

    @Schema(description = "Resposta da pessoa a questao", required = true)
    @NotNull(message = "The answer cannot be zero.")
    @NotBlank(message = "The answer cannot be blank")
    private String answer;


    public @NotNull(message = "The question ID is mandatory.") UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(@NotNull(message = "The question ID is mandatory.") UUID questionId) {
        this.questionId = questionId;
    }

    public UUID getResponseId() {
        return responseId;
    }

    public void setResponseId(UUID responseId) {
        this.responseId = responseId;
    }

    public @NotNull(message = "The person's ID is mandatory.") UUID getPersonId() {
        return personId;
    }

    public void setPersonId(@NotNull(message = "The person's ID is mandatory.") UUID personId) {
        this.personId = personId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public @NotNull(message = "The answer cannot be zero.") @NotBlank(message = "The answer cannot be blank") String getAnswer() {
        return answer;
    }

    public void setAnswer(@NotNull(message = "The answer cannot be zero.") @NotBlank(message = "The answer cannot be blank") String answer) {
        this.answer = answer;
    }
}
