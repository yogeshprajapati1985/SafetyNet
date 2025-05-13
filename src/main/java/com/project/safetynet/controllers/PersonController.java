package com.project.safetynet.controllers;

import com.project.safetynet.dto.PersonMedicalRecordDTO;
import com.project.safetynet.model.Person;
import com.project.safetynet.service.PersonService;
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
    public List<PersonMedicalRecordDTO> personInfo(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        return personService.getPersonInfo(firstName, lastName);
    }

    /**
     * @param city
     * @return This method returns list of the email of the persons living in the given city.
     */
    @GetMapping(path = "/communityEmail")
    public List<String> personInfo(@RequestParam String city) throws IOException {
        return personService.getCommunityEmail(city);
    }

    /**
     * This method should add a new person to the system.
     *
     * @param person
     * @throws IOException
     */
    @PostMapping(path = "/person")
    public void addPerson(@RequestBody Person person) throws IOException {
        personService.addPerson(person);
    }

    /**
     * This method should update an existing person in the system.
     *
     * @param updatedPerson
     * @throws IOException
     */
    @PutMapping(path = "/person")
    public void updatePerson(@RequestBody Person updatedPerson) throws IOException {
        personService.updatePerson(updatedPerson);
    }

    /**
     * This method should delete a person from the system based on the provided first and last name.
     *
     * @param firstName
     * @param lastName
     * @throws IOException
     */
    @DeleteMapping(path = "/person")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        personService.deletePerson(firstName, lastName);
    }
}