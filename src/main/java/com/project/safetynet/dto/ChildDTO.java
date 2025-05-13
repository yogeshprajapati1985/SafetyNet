package com.project.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class ChildDTO {
    private String firstName;
    private String lastName;
    private int age;
}
