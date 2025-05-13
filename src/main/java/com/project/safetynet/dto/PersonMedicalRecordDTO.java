package com.project.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class PersonMedicalRecordDTO {
    private String name;
    private String address;
    private int age;
    private String email;
    private String[] medications;
    private String[] allergies;
}
