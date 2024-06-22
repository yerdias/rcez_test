package com.example.controller;

import com.example.model.PersonModel;
import com.example.payload.PersonDto;
import com.example.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;


@WebMvcTest(PersonController.class) // Только для тестирования PersonController
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    private PersonDto personDto;
    private PersonModel personModel;

    @BeforeEach
    void setUp() {
        personDto = new PersonDto();
        personDto.setFirstName("John");
        personDto.setLastName("Doe");
        personDto.setBirthDate(LocalDate.parse("1990-01-01"));

        personModel = new PersonModel();
        personModel.setId(UUID.randomUUID());
        personModel.setFirstName("John");
        personModel.setLastName("Doe");
        personModel.setBirthDate(LocalDate.parse("1990-01-01"));
    }

    @Test
    void testSavePerson() throws Exception {
        BDDMockito.given(personService.save(ArgumentMatchers.any(PersonDto.class))).willReturn(personModel);

        mockMvc.perform(post("/persons/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
    @Test
    void testFindPersonById() throws Exception {
        UUID id = personModel.getId();
        BDDMockito.given(personService.findPersonById(id)).willReturn(personModel);

        mockMvc.perform(get("/persons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testUpdatePerson() throws Exception {
        UUID id = personModel.getId();
        BDDMockito.given(personService.updatePerson(id, personDto)).willReturn(personDto);

        mockMvc.perform(put("/persons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
    @Test
    void testGetListPersons() throws Exception {
        List<PersonModel> personList = Arrays.asList(personModel);

        BDDMockito.given(personService.getListPersons()).willReturn(personList);

        mockMvc.perform(get("/persons/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(personModel.getId().toString()))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].birthDate").value("1990-01-01"));
    }

    @Test
    void testFindPersonById_NotFound() throws Exception {
        UUID nonExistingId = UUID.randomUUID();

        // Имитируем, что сервис возвращает null, когда запрашивается несуществующий ID
        BDDMockito.given(personService.findPersonById(nonExistingId)).willReturn(null);

        mockMvc.perform(get("/persons/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Теперь ожидаем статус 404 Not Found
    }

}

