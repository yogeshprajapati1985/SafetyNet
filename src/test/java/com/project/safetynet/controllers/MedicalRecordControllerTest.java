package com.project.safetynet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.generators.TestDataGenerator;
import com.project.safetynet.model.MedicalRecord;
import com.project.safetynet.service.MedicalRecordService;
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

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void Given_Address_When_GetChildren_Called_Then_Return_ChildAlertDTO()
            throws Exception {

        //Given
        String address = "123 Main St";

        //When & Then
        mockMvc.perform(get("/childAlert")
                .contentType("application/json")
                .queryParam("address", address))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).getChildren(address);
    }

    @Test
    public void Given_MedicalRecord_When_AddMedicalRecord_Called_Then_Return_StatusOk()
            throws Exception {

        //Given
        MedicalRecord medicalRecord = TestDataGenerator.generateMedicalRecord();

        //When & Then
        mockMvc.perform(post("/medicalRecord")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).addMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void Given_MedicalRecord_When_UpdateMedicalRecord_Called_Then_Return_StatusOk()
            throws Exception {

        //Given
        MedicalRecord medicalRecord = TestDataGenerator.generateMedicalRecord();

        //When & Then
        mockMvc.perform(put("/medicalRecord")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).updateMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void Given_FirstName_AND_LastName_When_DeleteMedicalRecord_Called_Then_Return_StatusOk()
            throws Exception {

        //Given
        String firstName = "John";
        String lastName = "Doe";

        //When & Then
        mockMvc.perform(delete("/medicalRecord")
                .contentType("application/json")
                .queryParam("firstName", firstName)
                .queryParam("lastName", lastName))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).deleteMedicalRecord(firstName, lastName);
    }

}
