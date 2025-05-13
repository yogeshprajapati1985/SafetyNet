package com.project.safetynet.service;

import com.project.safetynet.dto.FireResponseDTO;
import com.project.safetynet.dto.FireStationResponseDTO;
import com.project.safetynet.dto.PersonDTO;
import com.project.safetynet.generators.TestDataGenerator;
import com.project.safetynet.mapper.SafetyNetMapper;
import com.project.safetynet.model.FireStation;
import com.project.safetynet.repository.FireStationRepository;
import com.project.safetynet.repository.MedicalRecordRepository;
import com.project.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class FireStationServiceImplTest {

    @Autowired
    private FireStationService  fireStationService;
    @MockitoBean
    private PersonRepository personRepository;
    @MockitoBean
    private FireStationRepository fireStationRepository;
    @MockitoBean
    private MedicalRecordRepository medicalRecordRepository;
    @MockitoBean
    private SafetyNetMapper mapper;

    @Test
    public void Given_fireStationNumbers_When_getHouseholdInfo_Called_Return_HouseholdInfo() throws Exception {
        // Given
        List<Integer> fireStationNumbers = List.of(1, 2);
        Map<String, Set<PersonDTO>> expected = Map.of("123 Main St", Set.of(TestDataGenerator.generatePersonDTO()));

        when(fireStationRepository.readFireStationsFromJSON()).thenReturn(TestDataGenerator.generateFireStationList());
        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        when(medicalRecordRepository.readMedicalRecordsFromJSON()).thenReturn(TestDataGenerator.generateMedicalRecordList());
        when(mapper.toHouseholdMedicalRecordDTOList(any(), any())).thenReturn(expected);

        // When
        Map<String, Set<PersonDTO>> actual = fireStationService.getHouseholdInfo(fireStationNumbers);

        // Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void Given_address_When_getFireData_Called_Return_FireResponseDTO() throws Exception {
        // Given
        String address = "123 Main St";
        FireResponseDTO expected = TestDataGenerator.generateFireResponseDTO();

        when(fireStationRepository.readFireStationsFromJSON()).thenReturn(TestDataGenerator.generateFireStationList());
        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        when(medicalRecordRepository.readMedicalRecordsFromJSON()).thenReturn(TestDataGenerator.generateMedicalRecordList());
        when(mapper.toFireResponseDTO(any(), any(), any())).thenReturn(expected);

        // When
        FireResponseDTO actual = fireStationService.getFireData(address);

        // Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void Given_FireStation_When_getPhoneNumbers_Called_Return_PhoneNumbers() throws Exception {
        // Given
        int station = 1;
        List<String> expected = List.of("841-874-6512");

        when(fireStationRepository.readFireStationsFromJSON()).thenReturn(TestDataGenerator.generateFireStationList());
        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        when(medicalRecordRepository.readMedicalRecordsFromJSON()).thenReturn(TestDataGenerator.generateMedicalRecordList());

        // When
        List<String> actual = fireStationService.getPhoneNumbers(station);

        // Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void Given_FireStation_When_getFireStationData_Called_Return_FireStationData() throws Exception {
        // Given
        int station = 1;
        FireStationResponseDTO expected = TestDataGenerator.generateFireStationResponseDTO();

        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        when(medicalRecordRepository.readMedicalRecordsFromJSON()).thenReturn(TestDataGenerator.generateMedicalRecordList());
        when(fireStationRepository.readFireStationsFromJSON()).thenReturn(TestDataGenerator.generateFireStationList());
        when(mapper.toFireStationResponseDTO(any(), any())).thenReturn(expected);

        // When
        FireStationResponseDTO actual = fireStationService.getFireStationData(station);

        // Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void Given_FireStation_When_addFireStation_Called_Throw_NoException() throws Exception {
        // Given
        FireStation newFireStation = TestDataGenerator.generateFireStation();
        doNothing().when(fireStationRepository).addFireStation(any());

        // When & Then
        assertDoesNotThrow(() -> fireStationService.addFireStation(newFireStation));
    }

    @Test
    public void Given_FireStation_When_updateFireStation_Called_Throw_NoException() throws Exception {
        // Given
        FireStation updatedFireStation = TestDataGenerator.generateFireStation();
        doNothing().when(fireStationRepository).updateFireStation(any());

        // When & Then
        assertDoesNotThrow(() -> fireStationService.updateFireStation(updatedFireStation));
    }

    @Test
    public void Given_AddressAndStation_When_deleteFireStation_Called_Throw_NoException() throws Exception {
        // Given
        String address = "123 Main St";
        int station = 1;
        doNothing().when(fireStationRepository).deleteFireStation(station);

        // When & Then
        assertDoesNotThrow(() -> fireStationService.deleteFireStation(station));
    }
}
