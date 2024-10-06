package com.wdev.sistema_de_cadastros.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PersonWithResponsesDTO {
    private UUID personId;
    private String name;
    private String email;
    private int age;
    private BigDecimal height;
    private List<ResponseDTO> responses;


    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public List<ResponseDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseDTO> responses) {
        this.responses = responses;
    }
}

