package com.project.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class MedicalRecord {
    @NotBlank(message = "firstName must not be blank.")
    private String firstName;
    @NotBlank(message = "lastName must not be blank.")
    private String lastName;
    @NotBlank(message = "Birthdate is required")
    @Pattern(
            regexp = "^(0[1-9]|1[0-2])/([0][1-9]|[12][0-9]|3[01])/\\d{4}$",
            message = "Birthdate must be in MM/dd/yyyy format"
    )
    private String birthdate;
    private String[] medications;
    private String[] allergies;
}
