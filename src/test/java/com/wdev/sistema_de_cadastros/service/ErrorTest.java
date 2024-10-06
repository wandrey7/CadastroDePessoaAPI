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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ErrorTest {
    @Autowired
    private MockMvc mockMvc;

    String personTest = """
            {
            \t"name": "John Doe Silva",
            \t"email": "JohnDoe@gmail.com",
            \t"age": 20,
            \t"height": "1,95"
            }""";

    @Test
    @DisplayName("Shold return error id not exists")
    void idNotExists() throws Exception {
        mockMvc.perform(delete("/person/352c63b3-36c6-4e6b-9760-351d3163ee63"))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value("There was an error deleting your id, check that it is correct"));
    }

    @Test
    @DisplayName("Shold return error id reserved")
    void idReserved() throws Exception {
        mockMvc.perform(delete("/questions/24b0d918-073b-42d0-8117-666a787ac2ef"))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value("This id cannot be deleted"));
    }


    @Test
    @DisplayName("Shold return error email existing")
    void emailExists() throws Exception {
        // Create a person
        MvcResult result = mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personTest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.personId").exists())
                .andExpect(jsonPath("$.data.name").value("John Doe Silva"))
                .andExpect(jsonPath("$.data.email").value("JohnDoe@gmail.com"))
                .andExpect(jsonPath("$.data.age").value(20))
                .andExpect(jsonPath("$.data.height").value(1.95))
                .andExpect(jsonPath("$.message").value("registration completed"))
                .andReturn();

        // Extracts data from the response
        String content = result.getResponse().getContentAsString();
        String personId = JsonPath.read(content, "$.data.personId");

        // Create a person2
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personTest))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Email already registered!"));

        // Delete person by id
        mockMvc.perform(delete("/person/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"successfully deleted\"}"));
    }
}
