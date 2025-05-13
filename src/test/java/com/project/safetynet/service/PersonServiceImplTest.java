package com.project.safetynet.service;

import com.project.safetynet.dto.PersonMedicalRecordDTO;
import com.project.safetynet.generators.TestDataGenerator;
import com.project.safetynet.mapper.SafetyNetMapper;
import com.project.safetynet.model.Person;
import com.project.safetynet.repository.MedicalRecordRepository;
import com.project.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class PersonServiceImplTest {

    @Autowired
    private PersonService personService;
    @MockitoBean
    private PersonRepository personRepository;
    @MockitoBean
    private MedicalRecordRepository medicalRecordRepository;
    @MockitoBean
    private SafetyNetMapper mapper;

    @Test
    public void Given_person_When_getPersonInfo_Called_Return_PersonInfo() throws Exception {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        List<PersonMedicalRecordDTO> expected = List.of(TestDataGenerator.generatePersonMedicalRecordDTO());

        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        when(medicalRecordRepository.readMedicalRecordsFromJSON()).thenReturn(TestDataGenerator.generateMedicalRecordList());
        when(mapper.toDTOList(any(), any())).thenReturn(expected);

        // When
        List<PersonMedicalRecordDTO> actual = personService.getPersonInfo(firstName, lastName);

        // Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void Given_city_When_getCommunityEmail_Called_Return_List_of_Community_Emails() throws Exception {
        // Given
        String city = "New York";
        List<String> expected = List.of("jaboyd@email.com");

        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());

        // When
        List<String> actual = personService.getCommunityEmail(city);

        // Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void Given_person_When_addPerson_Called_Return_Nothing() throws Exception {
        // Given
        Person person = TestDataGenerator.generatePerson();

        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        doNothing().when(personRepository).addPerson(any());

        // When & Then
        assertDoesNotThrow(() -> personService.addPerson(person));
    }

    @Test
    public void Given_person_When_updatePerson_Called_Return_Nothing() throws Exception {
        // Given
        Person updatedPerson = TestDataGenerator.generatePerson();

        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        doNothing().when(personRepository).updatePerson(any());

        // When & Then
        assertDoesNotThrow(() -> personService.updatePerson(updatedPerson));
    }

    @Test
    public void Given_person_When_deletePerson_Called_Return_Nothing() throws Exception {
        // Given
        String firstName = "John";
        String lastName = "Doe";

        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        doNothing().when(personRepository).deletePerson(any(), any());

        // When & Then
        assertDoesNotThrow(() -> personService.deletePerson(firstName, lastName));
    }

}
