package com.project.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.generators.TestDataGenerator;
import com.project.safetynet.model.Person;
import com.project.safetynet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private PersonService personService;

    @Test
    public void Given_FirstName_AND_LastName_When_GetPersonInfo_Called_Then_Return_PersonMedicalRecordDTO()
            throws Exception {

        //Given
        String firstName = "John";
        String lastName = "Doe";

        //When & Then
        mockMvc.perform(get("/personInfo")
                .contentType("application/json")
                .queryParam("firstName", firstName)
                .queryParam("lastName", lastName))
                .andExpect(status().isOk());

        verify(personService, times(1)).getPersonInfo(firstName, lastName);
    }

    @Test
    public void Given_City_When_GetCommunityEmail_Called_Then_Return_ListOfEmails()
            throws Exception {

        //Given
        String city = "New York";

        //When & Then
        mockMvc.perform(get("/communityEmail")
                .contentType("application/json")
                .queryParam("city", city))
                .andExpect(status().isOk());

        verify(personService, times(1)).getCommunityEmail(city);
    }

    @Test
    public void Given_Person_When_AddPerson_Called_Then_Return_StatusOk()
            throws Exception {

        //Given
        Person person = TestDataGenerator.generatePerson();

        //When & Then
        mockMvc.perform(post("/person")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(personService, times(1)).addPerson(any(Person.class));
    }

    @Test
    public void Given_Person_When_UpdatePerson_Called_Then_Return_StatusOk()
            throws Exception {

        //Given
        Person person = TestDataGenerator.generatePerson();

        //When & Then
        mockMvc.perform(put("/person")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(personService, times(1)).updatePerson(any(Person.class));
    }

    @Test
    public void Given_FirstName_AND_LastName_When_DeletePerson_Called_Then_Return_StatusOk()
            throws Exception {

        //Given
        String firstName = "John";
        String lastName = "Doe";

        //When & Then
        mockMvc.perform(delete("/person")
                .contentType("application/json")
                .queryParam("firstName", firstName)
                .queryParam("lastName", lastName))
                .andExpect(status().isOk());

        verify(personService, times(1)).deletePerson(firstName, lastName);
    }
}
