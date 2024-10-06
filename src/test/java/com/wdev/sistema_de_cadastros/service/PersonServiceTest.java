package com.wdev.sistema_de_cadastros.service;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PersonServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PersonService personService;

    String personTest = """
                        {
                        \t"name": "John Doe Silva",
                        \t"email": "JohnDoe@gmail.com",
                        \t"age": 20,
                        \t"height": "1,95"
                        }""";

    @Test
    @DisplayName("Shold return a create and delete Person")
    void createAndDeletePerson() throws Exception {
        // Create person
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

        // Delete person by id
        ResultActions deletePersonById = mockMvc.perform(delete("/person/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"successfully deleted\"}"));
    }

    @Test
    @DisplayName("Shold return a search person by name, age,email or all")
    void  searchPerson() throws Exception {
        // Create person
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
        String response = result.getResponse().getContentAsString();
        String personName = JsonPath.read(response, "$.data.name");
        String personEmail = JsonPath.read(response, "$.data.email");
        String personId = JsonPath.read(response, "$.data.personId");
        Integer personAge = JsonPath.read(response, "$.data.age");

        // Search person by name
        mockMvc.perform(get("/person?name={name}",personName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].name").value("John Doe Silva"));

        // Search person by email
        mockMvc.perform(get("/person?email={email}",personEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].email").value(personEmail));

        // Search person by age
        mockMvc.perform(get("/person?age={age}",personAge)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].age").value(personAge));

        // Delete person by id
        mockMvc.perform(delete("/person/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\": \"successfully deleted\"}"));

    }
}