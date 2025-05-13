package com.project.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.generators.TestDataGenerator;
import com.project.safetynet.model.FireStation;
import com.project.safetynet.model.MedicalRecord;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource(properties = "json.file.path=src/test/resources/test-data.json")
public class MedicalRecordRepositoryImplTest {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @MockitoBean
    private ObjectMapper objectMapper;

    @Test
    public void Given_Data_When_readMedicalRecordsFromJSON_Called_Return_MedicalRecord_List() throws IOException {
        // Given
        List<MedicalRecord> expected = TestDataGenerator.generateMedicalRecordList();
        Map<String, List<?>> allData = Map.of("medicalrecords", expected);
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);

        // When
        List<MedicalRecord> medicalRecords = medicalRecordRepository.readMedicalRecordsFromJSON();

        // Then
        assertNotNull(medicalRecords);
        assertFalse(medicalRecords.isEmpty());
        assertEquals(expected.size(), medicalRecords.size());
    }

    @Test
    public void Given_Data_When_addMedicalRecord_Called_Return_MedicalRecord_List() throws IOException {
        // Given
        MedicalRecord newMedicalRecord = TestDataGenerator.generateMedicalRecord();
        List<MedicalRecord> expected = new ArrayList<>();
        expected.add(newMedicalRecord);

        Map<String, List<?>> allData = new HashMap<>(Map.of("medicalrecords", expected));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);
        doNothing().when(objectMapper).writeValue(any(File.class), any(Map.class));

        // When
        medicalRecordRepository.addMedicalRecord(newMedicalRecord);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.readMedicalRecordsFromJSON();

        // Then
        assertNotNull(expected);
        assertFalse(expected.isEmpty());
        assertEquals(medicalRecords.size(), expected.size());
    }

    @Test
    public void Given_Data_When_updateMedicalRecord_Called_Return_MedicalRecord_List() throws IOException {
        // Given
        MedicalRecord updatedMedicalRecord = TestDataGenerator.generateMedicalRecord();
        List<MedicalRecord> expected = new ArrayList<>();
        expected.add(updatedMedicalRecord);

        Map<String, List<?>> allData = new HashMap<>(Map.of("medicalrecords", expected));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);
        doNothing().when(objectMapper).writeValue(any(File.class), any(Map.class));

        // When
        medicalRecordRepository.updateMedicalRecord(updatedMedicalRecord);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.readMedicalRecordsFromJSON();

        // Then
        assertNotNull(expected);
        assertFalse(expected.isEmpty());
        assertEquals(medicalRecords.size(), expected.size());
    }

    @Test
    public void Given_Data_When_deleteMedicalRecord_Called_Return_MedicalRecord_List() throws IOException {
        // Given
        MedicalRecord medicalRecordToDelete = TestDataGenerator.generateMedicalRecord();
        List<MedicalRecord> expected = new ArrayList<>();
        expected.add(medicalRecordToDelete);

        Map<String, List<?>> allData = new HashMap<>(Map.of("medicalrecords", expected));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);
        doNothing().when(objectMapper).writeValue(any(File.class), any(Map.class));

        // When
        medicalRecordRepository.deleteMedicalRecord(medicalRecordToDelete.getFirstName(),
                medicalRecordToDelete.getLastName());
        List<MedicalRecord> medicalRecords = medicalRecordRepository.readMedicalRecordsFromJSON();

        // Then
        assertNotNull(expected);
        assertTrue(expected.isEmpty());
    }
}
