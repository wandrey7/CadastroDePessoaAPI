package com.wdev.sistema_de_cadastros.model;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "QUESTIONS")
public class QuestionModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID questionId;

    private String questionText;

    private boolean isReserved;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<ResponseModel> responses;


    public QuestionModel() {
    }

    public QuestionModel(String questionText) {
        this.questionText = questionText;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}
