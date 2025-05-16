package com.project.safetynet.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class FireStation {
    @NotEmpty(message = "address must not be empty.")
    private String address;
    private int station;
}
