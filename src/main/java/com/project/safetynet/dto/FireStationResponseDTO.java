package com.project.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class FireStationResponseDTO {
    private List<PeopleDTO> people;
    private int numOfChildren;
    private int numOfAdults;
}
