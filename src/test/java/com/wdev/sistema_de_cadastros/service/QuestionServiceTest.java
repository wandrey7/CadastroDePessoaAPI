package com.wdev.sistema_de_cadastros.service;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QuestionServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("Shold return a get all questions")
    void getAllQuestions() throws Exception {
        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].questionText").exists())
                .andExpect(jsonPath("$.data[0].questionId").exists());
    }

    @Test
    @DisplayName("Shold returna a create question")
    void createQuestion() throws Exception {
        // Create a question
        MvcResult result = mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionText\": \"Random Question!\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Question created successfully!"))
                .andExpect(jsonPath("$.questionId").exists())
                .andReturn();
        // Extracts data from the response
        String content = result.getResponse().getContentAsString();
        String questionId = JsonPath.read(content, "$.questionId");

        // Delete response by id
        mockMvc.perform(delete("/questions/{id}", questionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("successfully deleted"));

    }

}
