package com.project.safetynet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String[] medications;
    private String[] allergies;
}
