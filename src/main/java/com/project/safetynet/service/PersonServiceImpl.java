package com.project.safetynet.service;

import com.project.safetynet.dto.PersonMedicalRecordDTO;
import com.project.safetynet.mapper.SafetyNetMapper;
import com.project.safetynet.model.Person;
import com.project.safetynet.repository.MedicalRecordRepository;
import com.project.safetynet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private SafetyNetMapper mapper;

    /**
     * This method retrieves a list of PersonMedicalRecordDTO objects based on the provided first and last name.
     *
     * @param firstName the first name of the person
     * @param lastName  the last name of the person
     * @return a list of PersonMedicalRecordDTO objects matching the provided first and last name
     */
    @Override
    public List<PersonMedicalRecordDTO> getPersonInfo(String firstName, String lastName) throws IOException {
        List<Person> filteredPersons = personRepository.readPersonsFromJSON().stream()
                .filter(person -> person.getFirstName().equals(firstName)
                && person.getLastName().equals(lastName)).collect(Collectors.toList());
        return mapper.toDTOList(filteredPersons, medicalRecordRepository.readMedicalRecordsFromJSON());
    }

    /**
     * @param city
     * @return This method returns list of the email of the persons living in the given city.
     */
    @Override
    public List<String> getCommunityEmail(String city) throws IOException  {
        List<Person> filteredPersons = personRepository.readPersonsFromJSON().stream()
                .filter(person -> person.getCity().equals(city)).collect(Collectors.toList());

        List<String> emails = filteredPersons.stream()
                .map(Person::getEmail).distinct()
                .collect(Collectors.toList());

        return emails;
    }

    /**
     * @param person
     *
     * @throws IOException
     */
    @Override
    public void addPerson(Person person) throws IOException {
        personRepository.addPerson(person);
    }

    /**
     * @param updatedPerson
     * @throws IOException
     */
    @Override
    public void updatePerson(Person updatedPerson) throws IOException {
        personRepository.updatePerson(updatedPerson);
    }

    /**
     * @param firstName
     * @param lastName
     * @throws IOException
     */
    @Override
    public void deletePerson(String firstName, String lastName) throws IOException {
        personRepository.deletePerson(firstName, lastName);
    }
}
