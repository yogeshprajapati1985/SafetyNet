package com.project.safetynet.service;

import com.project.safetynet.dto.PersonMedicalRecordDTO;
import com.project.safetynet.model.Person;

import java.io.IOException;
import java.util.List;

public interface PersonService {
    List<PersonMedicalRecordDTO> getPersonInfo(String firstName, String lastName) throws IOException;
    List<String> getCommunityEmail(String city) throws IOException;
    void addPerson(Person person) throws IOException;
    void updatePerson(Person updatedPerson) throws IOException;
    void deletePerson(String firstName, String lastName) throws IOException;
}
