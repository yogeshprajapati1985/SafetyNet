package com.project.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.generators.TestDataGenerator;
import com.project.safetynet.model.FireStation;
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
public class FireStationRepositoryImplTest {
    @Autowired
    private FireStationRepository fireStationRepository;

    @MockitoBean
    private ObjectMapper objectMapper;

    @Test
    public void Given_Data_When_readFireStationsFromJSON_Called_Return_FireStation_List() throws IOException {
        // Given
        List<FireStation> expected = TestDataGenerator.generateFireStationList();
        Map<String, List<?>> allData = Map.of("firestations", expected);
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);

        // When
        List<FireStation> fireStations = fireStationRepository.readFireStationsFromJSON();

        // Then
        assertNotNull(fireStations);
        assertFalse(fireStations.isEmpty());
        assertEquals(expected.size(), fireStations.size());
    }

    @Test
    public void Given_Data_When_addFireStation_Called_Return_FireStation_List() throws IOException {
        // Given
        FireStation newFireStation = TestDataGenerator.generateFireStation();
        List<FireStation> expected = new ArrayList<>();
        expected.add(newFireStation);

        Map<String, List<?>> allData = new HashMap<>(Map.of("firestations", expected));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);
        doNothing().when(objectMapper).writeValue(any(File.class), any(Map.class));

        // When
        fireStationRepository.addFireStation(newFireStation);
        List<FireStation> fireStations = fireStationRepository.readFireStationsFromJSON();

        // Then
        assertNotNull(expected);
        assertFalse(expected.isEmpty());
        assertEquals(fireStations.size(), expected.size());
    }

    @Test
    public void Given_Data_When_updateFireStation_Called_Return_FireStation_List() throws IOException {
        // Given
        FireStation updatedFireStation = TestDataGenerator.generateFireStation();
        List<FireStation> expected = new ArrayList<>();
        expected.add(updatedFireStation);

        Map<String, List<?>> allData = new HashMap<>(Map.of("firestations", expected));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);
        doNothing().when(objectMapper).writeValue(any(File.class), any(Map.class));

        // When
        fireStationRepository.updateFireStation(updatedFireStation);
        List<FireStation> fireStations = fireStationRepository.readFireStationsFromJSON();

        // Then
        assertNotNull(fireStations);
        assertFalse(fireStations.isEmpty());
        assertEquals(fireStations.size(), expected.size());
    }

    @Test
    public void Given_Data_When_deleteFireStation_Called_Return_FireStation_List() throws IOException {
        // Given
        FireStation fireStationToDelete = TestDataGenerator.generateFireStation();
        List<FireStation> expected = new ArrayList<>();
        expected.add(fireStationToDelete);

        Map<String, List<?>> allData = new HashMap<>(Map.of("firestations", expected));
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(allData);
        when(objectMapper.convertValue(any(), any(TypeReference.class))).thenReturn(expected);
        doNothing().when(objectMapper).writeValue(any(File.class), any(Map.class));

        // When
        fireStationRepository.deleteFireStation(fireStationToDelete.getStation());
        List<FireStation> fireStations = fireStationRepository.readFireStationsFromJSON();

        // Then
        assertNotNull(fireStations);
        assertTrue(fireStations.isEmpty());
        assertEquals(fireStations.size(), expected.size());
    }
}
