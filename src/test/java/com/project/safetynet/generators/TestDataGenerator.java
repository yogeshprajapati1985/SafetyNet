package com.project.safetynet.generators;

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

import java.util.List;

public class TestDataGenerator {
    public static Person generatePerson(){
        return new Person("John", "Doe", "123 Main St", "New York", "44136", "841-874-6512", "jaboyd@email.com");
    }

    public static List<Person> generatePersonList(){
        return List.of(
                generatePerson()
        );
    }

    public static MedicalRecord generateMedicalRecord(){
        return new MedicalRecord("John", "Doe", "01/01/2000", new String[] {"aznol:350mg", "hydrapermazol:100mg"}, new String[] {"nillacilan:250mg", "thramycin:100mg"});
    }

    public static List<MedicalRecord> generateMedicalRecordList(){
        return List.of(
                generateMedicalRecord()
        );
    }

    public static FireStation generateFireStation(){
        return new FireStation("123 Main St", 1);
    }

    public static List<FireStation> generateFireStationList(){
        return List.of(
                generateFireStation()
        );
    }

    public static ChildDTO generateChildDTO(){
        return new ChildDTO("John", "Doe", 25);
    }

    public static PersonDTO generatePersonDTO(){
        return new PersonDTO("John", 25, "841-874-6512", new String[] {"aznol:350mg", "hydrapermazol:100mg"}, new String[] {"nillacilan:250mg", "thramycin:100mg"});
    }

    public static ChildAlertDTO generateChildAlertDTO(){
        return new ChildAlertDTO(List.of(generateChildDTO()), List.of(generatePersonDTO()));
    }

    public static FireResponseDTO generateFireResponseDTO() {
        return FireResponseDTO.builder()
                .station(1)
                .persons(List.of(generatePersonDTO()))
                .build();
    }

    public static PersonMedicalRecordDTO generatePersonMedicalRecordDTO() {
        return new PersonMedicalRecordDTO("John", "123 Main St", 25, "jaboyd@email.com", new String[] {"aznol:350mg", "hydrapermazol:100mg"}, new String[] {"nillacilan:250mg", "thramycin:100mg"});
    }

    public static PeopleDTO generatePeopleDTO(){
        return new PeopleDTO("John", "Doe", "123 Main St", "841-874-6512");
    }
    public static FireStationResponseDTO generateFireStationResponseDTO() {
        return FireStationResponseDTO.builder()
                .people(List.of(generatePeopleDTO()))
                .numOfChildren(1)
                .numOfAdults(1)
                .build();
    }
}
