package com.project.safetynet.repository;

import com.project.safetynet.model.MedicalRecord;
import com.project.safetynet.model.Person;

import java.io.IOException;
import java.util.List;

public interface PersonRepository {
    List<Person> readPersonsFromJSON() throws IOException;
    void addPerson(Person person) throws IOException;
    void updatePerson(Person updatedPerson) throws IOException;
    void deletePerson(String firstName, String lastName) throws IOException;
}
