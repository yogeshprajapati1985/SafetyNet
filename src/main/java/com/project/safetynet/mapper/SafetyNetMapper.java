package com.project.safetynet.mapper;

import com.project.safetynet.dto.ChildAlertDTO;
import com.project.safetynet.dto.ChildDTO;
import com.project.safetynet.dto.FireResponseDTO;
import com.project.safetynet.dto.FireStationResponseDTO;
import com.project.safetynet.dto.PeopleDTO;
import com.project.safetynet.dto.PersonDTO;
import com.project.safetynet.dto.PersonMedicalRecordDTO;
import com.project.safetynet.model.FireStation;
import com.project.safetynet.model.MedicalRecord;
import com.project.safetynet.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SafetyNetMapper {


    @Mapping(target = "name", expression = "java(person.getFirstName() + ' ' + person.getLastName())")
    @Mapping(target = "address",
                        expression = "java(person.getAddress() + ' ' + person.getCity() + ' ' + person.getZip())")
    @Mapping(target = "email", source = "person.email")
    @Mapping(target = "age", expression = "java(calculateAge(medicalRecord.getBirthdate()))")
    @Mapping(target = "medications", source = "medicalRecord.medications")
    @Mapping(target = "allergies", source = "medicalRecord.allergies")
    PersonMedicalRecordDTO toDTO(Person person, MedicalRecord medicalRecord);

    @Mapping(target = "name", expression = "java(person.getFirstName() + ' ' + person.getLastName())")
    @Mapping(target = "phone", source = "person.phone")
    @Mapping(target = "age", expression = "java(calculateAge(medicalRecord.getBirthdate()))")
    @Mapping(target = "medications", source = "medicalRecord.medications")
    @Mapping(target = "allergies", source = "medicalRecord.allergies")
    PersonDTO toPersonDTO(Person person, MedicalRecord medicalRecord);

    /**
     * Calculates the age based on the provided birth date string.
     *
     * @param birthDateStr the birth date string in the format "MM/dd/yyyy"
     * @return the calculated age
     */
    default int calculateAge(String birthDateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);

        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Converts a list of Person objects and a list of MedicalRecord objects into a list of PersonMedicalRecordDTO objects.
     *
     * @param persons        the list of Person objects
     * @param medicalRecords the list of MedicalRecord objects
     * @return a list of PersonMedicalRecordDTO objects
     */
    default List<PersonMedicalRecordDTO> toDTOList(List<Person> persons, List<MedicalRecord> medicalRecords) {
        return persons.stream()
                .map(person -> {
                    MedicalRecord medicalRecord = medicalRecords.stream()
                            .filter(record -> record.getFirstName().equals(person.getFirstName())
                                    && record.getLastName().equals(person.getLastName()))
                            .findFirst()
                            .orElse(null);
                    return toDTO(person, medicalRecord);
                })
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of Person objects and a list of MedicalRecord objects into a list of PersonDTO objects.
     *
     * @param persons        the list of Person objects
     * @param medicalRecords the list of MedicalRecord objects
     * @return a list of PersonDTO objects
     */
    default List<PersonDTO> toPersonDtoList(List<Person> persons, List<MedicalRecord> medicalRecords) {
        return persons.stream()
                .map(person -> {
                    MedicalRecord medicalRecord = medicalRecords.stream()
                            .filter(record -> record.getFirstName().equals(person.getFirstName())
                                    && record.getLastName().equals(person.getLastName()))
                            .findFirst()
                            .orElse(null);
                    return toPersonDTO(person, medicalRecord);
                })
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of Person objects and a list of MedicalRecord objects into a map where the key is the address
     * and the value is a set of PersonDTO objects.
     *
     * @param persons        the list of Person objects
     * @param medicalRecords the list of MedicalRecord objects
     * @return a map where the key is the address and the value is a set of PersonDTO objects
     */
    default Map<String, Set<PersonDTO>> toHouseholdMedicalRecordDTOList(List<Person> persons,
                                                                        List<MedicalRecord> medicalRecords) {
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getAddress,
                        Collectors.mapping(person -> {
                            MedicalRecord medicalRecord = medicalRecords.stream()
                                    .filter(record -> record.getFirstName().equals(person.getFirstName())
                                            && record.getLastName().equals(person.getLastName()))
                                    .findFirst()
                                    .orElse(null);
                            return PersonDTO.builder()
                                    .name(person.getFirstName() + " " + person.getLastName())
                                    .age(calculateAge(medicalRecord.getBirthdate()))
                                    .phone(person.getPhone())
                                    .medications(medicalRecord.getMedications())
                                    .allergies(medicalRecord.getAllergies())
                                    .build();
                        }, Collectors.toSet())));
    }


    /**     * Converts a FireStation object and lists of Person and MedicalRecord objects into a FireResponseDTO object.
     *
     * @param fireStation the FireStation object
     * @param persons     the list of Person objects
     * @param medicalRecords the list of MedicalRecord objects
     * @return a FireResponseDTO object containing the fire station number, and a list of PersonDTO objects
     */
    default FireResponseDTO toFireResponseDTO(FireStation fireStation, List<Person> persons,
                                              List<MedicalRecord> medicalRecords) {
        return FireResponseDTO.builder()
                .station(fireStation.getStation())
                .persons(toPersonDtoList(persons, medicalRecords))
                .build();

    }

    default ChildAlertDTO toChildAlertDTO(List<Person> filteredPersons, List<MedicalRecord> medicalRecords){

        Map<Boolean, List<MedicalRecord>> partitionedMedicalRecords = medicalRecords.stream()
                .collect(Collectors.partitioningBy(medicalRecord -> calculateAge(medicalRecord.getBirthdate()) < 18));

        List<ChildDTO> children = partitionedMedicalRecords.get(true).stream()
                .map(medicalRecord -> {
                    Person person = filteredPersons.stream()
                            .filter(p -> p.getFirstName().equals(medicalRecord.getFirstName())
                                    && p.getLastName().equals(medicalRecord.getLastName()))
                            .findFirst()
                            .orElse(null);
                    return ChildDTO.builder()
                            .firstName(person.getFirstName())
                            .lastName(person.getLastName())
                            .age(calculateAge(medicalRecord.getBirthdate()))
                            .build();
                })
                .collect(Collectors.toList());

        if(children.isEmpty()){
            return ChildAlertDTO.builder()
                    .children(children)
                    .persons(List.of())
                    .build();
        }
        List<PersonDTO> adults = partitionedMedicalRecords.get(false).stream()
                .map(medicalRecord -> {
                    Person person = filteredPersons.stream()
                            .filter(p -> p.getFirstName().equals(medicalRecord.getFirstName())
                                    && p.getLastName().equals(medicalRecord.getLastName()))
                            .findFirst()
                            .orElse(null);
                    return PersonDTO.builder()
                            .name(person.getFirstName() + " " + person.getLastName())
                            .age(calculateAge(medicalRecord.getBirthdate()))
                            .phone(person.getPhone())
                            .medications(medicalRecord.getMedications())
                            .allergies(medicalRecord.getAllergies())
                            .build();
                })
                .collect(Collectors.toList());


        return ChildAlertDTO.builder()
                .children(children)
                .persons(adults)
                .build();
    }

    PeopleDTO toPeopleDTO(Person person);

    default List<PeopleDTO> toPeopleDtoList(List<Person> persons) {
        return persons.stream()
                .map(person -> toPeopleDTO(person))
                .collect(Collectors.toList());
    }

    default FireStationResponseDTO toFireStationResponseDTO(List<Person> filteredPersons, List<MedicalRecord> medicalRecords){
        List<PeopleDTO> listOfPeople = toPeopleDtoList(filteredPersons);
        long adultCount = medicalRecords.stream().filter(
                medicalRecord -> calculateAge(medicalRecord.getBirthdate()) >= 18).count();
        long childCount = medicalRecords.stream().count()-adultCount;

        return FireStationResponseDTO.builder()
                .people(listOfPeople)
                .numOfAdults((int) adultCount)
                .numOfChildren((int) childCount)
                .build();
    }
}
