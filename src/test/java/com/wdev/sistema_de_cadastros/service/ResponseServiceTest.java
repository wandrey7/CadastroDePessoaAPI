package com.wdev.sistema_de_cadastros.service;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResponseServiceTest {
    @Autowired
    private MockMvc mockMvc;

    String personTest = """
                        {
                        \t"name": "Maria Doe Silva",
                        \t"email": "MariaDoe@gmail.com",
                        \t"age": 20,
                        \t"height": "1,95"
                        }""";

    @Test
    @DisplayName("Shold return a create response")
    void createResponse() throws Exception {
        // create person
        MvcResult resultPerson = mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personTest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.personId").exists())
                .andExpect(jsonPath("$.data.name").value("Maria Doe Silva"))
                .andExpect(jsonPath("$.data.email").value("MariaDoe@gmail.com"))
                .andExpect(jsonPath("$.data.age").value(20))
                .andExpect(jsonPath("$.data.height").value(1.95))
                .andExpect(jsonPath("$.message").value("registration completed"))
                .andReturn();

        // create question
        MvcResult resultQuestion = mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionText\": \"A random question\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Question created successfully!"))
                .andReturn();

        // Extracts data from the response
        String contentPerson = resultPerson.getResponse().getContentAsString();
        String personId = JsonPath.read(contentPerson, "$.data.personId");
        String contentQuestion = resultQuestion.getResponse().getContentAsString();
        String questionId = JsonPath.read(contentQuestion, "$.questionId");
        String answer = "Valid response";

        // create response
        MvcResult result = mockMvc.perform(post("/responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{ \"personId\": \"%s\", \"questionId\": \"%s\", \"answer\": \"%s\" }",
                                personId, questionId, answer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Response successfully registered"))
                .andReturn();

        // Extracts data from the response
        String content = result.getResponse().getContentAsString();
        String responseId  = JsonPath.read(content, "$.responseId");

        // Delete question by id
        mockMvc.perform(delete("/questions/{id}", questionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"successfully deleted\"}"));

        // Delete person by id
        mockMvc.perform(delete("/person/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"successfully deleted\"}"));
    }
}
