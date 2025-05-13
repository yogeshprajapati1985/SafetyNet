package com.project.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private FireStationService fireStationService;

    @Test
    public void Given_Stations_When_GetHouseholds_Called_Then_Return_Households() throws Exception {
        // Given
        String stations = "1,2,3";

        // When & Then
        mockMvc.perform(get("/flood/stations")
                .contentType("application/json")
                .queryParam("stations", stations))
                .andExpect(status().isOk());
    }

    @Test
    public void Given_Address_When_GetFireData_Called_Then_Return_FireResponseDTO() throws Exception {
        // Given
        String address = "123 Main St";

        // When & Then
        mockMvc.perform(get("/fire")
                .contentType("application/json")
                .queryParam("address", address))
                .andExpect(status().isOk());
    }

    @Test
    public void Given_Firestation_When_GetPhoneNumbers_Called_Then_Return_PhoneNumbers() throws Exception {
        // Given
        int firestation = 1;

        // When & Then
        mockMvc.perform(get("/phoneAlert")
                .contentType("application/json")
                .queryParam("firestation", String.valueOf(firestation)))
                .andExpect(status().isOk());
    }

    @Test
    public void Given_Firestation_When_GetFirestationCoverage_Called_Then_Return_FirestationCoverage() throws Exception {
        // Given
        int firestation = 1;

        // When & Then
        mockMvc.perform(get("/firestation")
                .contentType("application/json")
                .queryParam("stationNumber", String.valueOf(firestation)))
                .andExpect(status().isOk());
    }

    @Test
    public void Given_FireStation_When_AddFireStation_Called_Then_Return_StatusOk() throws Exception {
        // Given
        String fireStation = "{ \"station\": 1, \"address\": \"123 Main St\" }";

        // When & Then
        mockMvc.perform(post("/firestation")
                .contentType("application/json")
                .content(fireStation))
                .andExpect(status().isOk());
    }

    @Test
    public void Given_FireStation_When_UpdateFireStation_Called_Then_Return_StatusOk() throws Exception {
        // Given
        String fireStation = "{ \"station\": 1, \"address\": \"123 Main St\" }";

        // When & Then
        mockMvc.perform(put("/firestation")
                .contentType("application/json")
                .content(fireStation))
                .andExpect(status().isOk());
    }

    @Test
    public void Given_FireStation_When_DeleteFireStation_Called_Then_Return_StatusOk() throws Exception {
        // Given
        int firestation = 1;

        // When & Then
        mockMvc.perform(delete("/firestation")
                .contentType("application/json")
                .queryParam("station", String.valueOf(firestation)))
                .andExpect(status().isOk());
    }
}
