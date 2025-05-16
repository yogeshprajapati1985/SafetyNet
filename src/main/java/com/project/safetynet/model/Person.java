package com.project.safetynet.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class Person {
    @NotBlank(message = "firstName must not be blank.")
    private String firstName;
    @NotBlank(message = "lastName must not be blank.")
    private String lastName;
    @NotBlank(message = "address must not be blank.")
    private String address;
    @NotBlank(message = "city must not be blank.")
    private String city;
    @NotBlank(message = "zip must not be blank.")
    private String zip;
    @NotBlank(message = "phone must not be blank.")
    private String phone;
    @NotBlank(message = "email must not be blank.")
    private String email;
}
