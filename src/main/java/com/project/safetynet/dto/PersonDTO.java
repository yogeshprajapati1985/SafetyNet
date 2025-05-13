package com.project.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class PersonDTO {
    private String name;
    private int age;
    private String phone;
    private String[] medications;
    private String[] allergies;
}
