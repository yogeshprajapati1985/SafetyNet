package com.project.safetynet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class PeopleDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
}
