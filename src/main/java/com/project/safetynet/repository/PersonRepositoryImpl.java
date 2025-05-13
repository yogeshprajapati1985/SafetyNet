package com.project.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl extends BaseRepository implements PersonRepository {

    public PersonRepositoryImpl(ObjectMapper objectMapper, @Value("${json.file.path}" ) String jsonFilePath) {
        super(objectMapper);
        this.jsonFilePath = jsonFilePath;
    }
    @Override
    public List<Person> readPersonsFromJSON() throws IOException {

            Map<String, List<?>> allData = getAllData();
        return objectMapper.convertValue(allData.get("persons"), new TypeReference<List<Person>>() {});
    }

    @Override
    public void addPerson(Person person) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<Person> persons = objectMapper.convertValue(allData.get("persons"), new TypeReference<List<Person>>() {});
        persons.add(person);
        allData.put("persons", persons);
        objectMapper.writeValue(new File(jsonFilePath), allData);
    }

    @Override
    public void updatePerson(Person updatedPerson) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<Person> persons = objectMapper.convertValue(allData.get("persons"), new TypeReference<List<Person>>() {});
        Optional<Person> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(updatedPerson.getFirstName())
                        && p.getLastName().equals(updatedPerson.getLastName()))
                .findFirst();
        if (existingPerson.isPresent()) {
            Person person = existingPerson.get();
            person.setAddress(updatedPerson.getAddress());
            person.setCity(updatedPerson.getCity());
            person.setZip(updatedPerson.getZip());
            person.setPhone(updatedPerson.getPhone());
            person.setEmail(updatedPerson.getEmail());
            allData.put("persons", persons);
            objectMapper.writeValue(new File(jsonFilePath), allData);
        } else{
            throw new RuntimeException("Person not found");
        }
    }

    @Override
    public void deletePerson(String firstName, String lastName) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<Person> persons = objectMapper.convertValue(allData.get("persons"), new TypeReference<List<Person>>() {});
        Optional<Person> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst();
        if (existingPerson.isPresent()) {
            persons.remove(existingPerson.get());
            allData.put("persons", persons);
            objectMapper.writeValue(new File(jsonFilePath), allData);
        } else{
            throw new RuntimeException("Person not found");
        }
    }
}
