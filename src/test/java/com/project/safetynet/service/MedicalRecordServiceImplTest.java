package com.project.safetynet.service;

import com.project.safetynet.dto.ChildAlertDTO;
import com.project.safetynet.generators.TestDataGenerator;
import com.project.safetynet.mapper.SafetyNetMapper;
import com.project.safetynet.model.MedicalRecord;
import com.project.safetynet.repository.MedicalRecordRepository;
import com.project.safetynet.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class MedicalRecordServiceImplTest {

    @Autowired
    private MedicalRecordService medicalRecordService;
    @MockitoBean
    private PersonRepository personRepository;
    @MockitoBean
    private MedicalRecordRepository medicalRecordRepository;
    @MockitoBean
    private SafetyNetMapper mapper;

    @Test
    public void Given_address_When_getChildren_Called_Return_ChildAlertDTO() throws Exception {
        // Given
        String address = "123 Main St";

        when(personRepository.readPersonsFromJSON()).thenReturn(TestDataGenerator.generatePersonList());
        when(medicalRecordRepository.readMedicalRecordsFromJSON()).thenReturn(TestDataGenerator.generateMedicalRecordList());
        when(mapper.toChildAlertDTO(any(), any())).thenReturn(TestDataGenerator.generateChildAlertDTO());

        // When
        ChildAlertDTO childAlertDTO = medicalRecordService.getChildren(address);

        // Then
        assertNotNull(childAlertDTO);
        assertFalse(childAlertDTO.getChildren().isEmpty());
    }

    @Test
    public void Given_MedicalRecord_When_addMedicalRecord_Called_Throw_NoException() throws Exception {
        // Given
        MedicalRecord newMedicalRecord = TestDataGenerator.generateMedicalRecord();
        doNothing().when(medicalRecordRepository).addMedicalRecord(any());

        // When & Then
        assertDoesNotThrow(() -> medicalRecordService.addMedicalRecord(newMedicalRecord));
    }

    @Test
    public void Given_MedicalRecord_When_updateMedicalRecord_Called_Throw_NoException() throws Exception {
        // Given
        MedicalRecord updatedMedicalRecord = TestDataGenerator.generateMedicalRecord();
        doNothing().when(medicalRecordRepository).updateMedicalRecord(any());

        // When & Then
        assertDoesNotThrow(() -> medicalRecordService.updateMedicalRecord(updatedMedicalRecord));
    }

    @Test
    public void Given_FirstNameAndLastName_When_deleteMedicalRecord_Called_Throw_NoException() throws Exception {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        doNothing().when(medicalRecordRepository).deleteMedicalRecord(firstName, lastName);

        // When & Then
        assertDoesNotThrow(() -> medicalRecordService.deleteMedicalRecord(firstName, lastName));
    }
}
