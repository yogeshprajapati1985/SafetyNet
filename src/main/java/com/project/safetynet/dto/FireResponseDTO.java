package com.project.safetynet.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class FireResponseDTO {
    private int station;
    private List<PersonDTO> persons;
}
