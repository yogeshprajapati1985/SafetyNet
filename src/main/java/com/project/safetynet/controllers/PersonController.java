package com.project.safetynet.controllers;

import com.project.safetynet.dto.PersonMedicalRecordDTO;
import com.project.safetynet.model.Person;
import com.project.safetynet.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * This method retrieves a list of PersonMedicalRecordDTO objects based on the provided first and last name.
     *
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     * @return a list of PersonMedicalRecordDTO objects matching the provided first and last name
     */
    @GetMapping(path = "/personInfo")
    public List<PersonMedicalRecordDTO> personInfo(@RequestParam @NotBlank(message = "firstName is required.") String firstName,
                                                   @RequestParam @NotBlank(message = "lastName is required.") String lastName) throws IOException {
        return personService.getPersonInfo(firstName, lastName);
    }

    /**
     * @param city city name
     * @return This method returns list of the email of the persons living in the given city.
     */
    @GetMapping(path = "/communityEmail")
    public List<String> personInfo(@RequestParam @NotBlank(message = "city is required.") String city) throws IOException {
        return personService.getCommunityEmail(city);
    }

    /**
     * This method should add a new person to the system.
     *
     * @param person the person to be added
     * @throws IOException throws IOException if there is an error during the operation
     */
    @PostMapping(path = "/person")
    public void addPerson(@RequestBody @Valid Person person) throws IOException {
        personService.addPerson(person);
    }

    /**
     * This method should update an existing person in the system.
     *
     * @param updatedPerson the person to be updated
     * @throws IOException throws IOException if there is an error during the operation
     */
    @PutMapping(path = "/person")
    public void updatePerson(@RequestBody @Valid Person updatedPerson) throws IOException {
        personService.updatePerson(updatedPerson);
    }

    /**
     * This method should delete a person from the system based on the provided first and last name.
     *
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @throws IOException throws IOException if there is an error during the operation
     */
    @DeleteMapping(path = "/person")
    public void deletePerson(@RequestParam @NotBlank(message = "firstName is required.") String firstName,
                             @RequestParam @NotBlank(message = "lastName is required.") String lastName) throws IOException {
        personService.deletePerson(firstName, lastName);
    }
}