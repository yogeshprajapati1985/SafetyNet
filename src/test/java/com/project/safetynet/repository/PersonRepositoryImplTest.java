package com.project.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.generators.TestDataGenerator;
import com.project.safetynet.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource(properties = "json.file.path=src/test/resources/test-data.json")
public class PersonRepositoryImplTest {

    @Autowired
    private PersonRepository personRepository;

    @MockitoBean
    private ObjectMapper objectMapper;

    @Test
    public void Given_Data_When_readPersonsFromJSON_Called_Return_Person_List() throws IOException {
        // Given
        List<Person> expected = TestDataGenerator.generatePersonList();
        Map<String, List<?>> allData = Map.of("persons", expected);
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);

        // When
        List<Person> persons = personRepository.readPersonsFromJSON();

        // Then
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
        assertEquals(expected.size(), persons.size());
    }

    @Test
    public void Given_Data_When_addPerson_Called_Return_Person_List() throws IOException {
        // Given
        Person newPerson = TestDataGenerator.generatePerson();
        List<Person> expected = new ArrayList<>();
        expected.add(newPerson);

        Map<String, List<?>> allData = new HashMap<>(Map.of("persons", expected));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);
        doNothing().when(objectMapper).writeValue(any(File.class), any());

        // When
        personRepository.addPerson(newPerson);
        List<Person> persons = personRepository.readPersonsFromJSON();

        // Then
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
        assertEquals(expected.size(), persons.size());
    }

    @Test
    public void Given_Data_When_updatePerson_Called_Return_Person_List() throws IOException {
        // Given
        Person updatedPerson = TestDataGenerator.generatePerson();
        List<Person> expected = new ArrayList<>();
        expected.add(updatedPerson);

        Map<String, List<?>> allData = new HashMap<>(Map.of("persons", expected));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);
        doNothing().when(objectMapper).writeValue(any(File.class), any());

        // When
        personRepository.updatePerson(updatedPerson);
        List<Person> persons = personRepository.readPersonsFromJSON();

        // Then
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
        assertEquals(expected.size(), persons.size());
    }

    @Test
    public void Given_Data_When_deletePerson_Called_Return_Person_List() throws IOException {
        // Given
        Person personToDelete = TestDataGenerator.generatePerson();
        List<Person> personList = new ArrayList<>();
        personList.add(personToDelete);

        Map<String, List<?>> allData = new HashMap<>(Map.of("persons", personList));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(personList);
        doNothing().when(objectMapper).writeValue(any(File.class), any());

        // When
        personRepository.deletePerson(personToDelete.getFirstName(), personToDelete.getLastName());
        List<Person> persons = personRepository.readPersonsFromJSON();

        // Then
        assertNotNull(persons);
        assertTrue(persons.isEmpty());
        assertEquals(personList.size(), persons.size());
    }

}
