package com.project.safetynet.service;

import com.project.safetynet.dto.ChildAlertDTO;
import com.project.safetynet.mapper.SafetyNetMapper;
import com.project.safetynet.model.MedicalRecord;
import com.project.safetynet.model.Person;
import com.project.safetynet.repository.MedicalRecordRepository;
import com.project.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final SafetyNetMapper mapper;

    /**
     * @param address
     * @return
     */
    @Override
    public ChildAlertDTO getChildren(String address) throws IOException {

        List<Person> filteredPersons = personRepository.readPersonsFromJSON().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());

        List<MedicalRecord> medicalRecords = medicalRecordRepository.readMedicalRecordsFromJSON().stream()
                .filter(medicalRecord -> filteredPersons.stream()
                        .anyMatch(person -> person.getFirstName().equals(medicalRecord.getFirstName())
                                && person.getLastName().equals(medicalRecord.getLastName()))).toList();

        return mapper.toChildAlertDTO(filteredPersons, medicalRecords);
    }

    /**
     * @param record
     * @throws IOException
     */
    @Override
    public void addMedicalRecord(MedicalRecord record) throws IOException {
        medicalRecordRepository.addMedicalRecord(record);
    }

    /**
     * @param updatedRecord
     * @throws IOException
     */
    @Override
    public void updateMedicalRecord(MedicalRecord updatedRecord) throws IOException {
        medicalRecordRepository.updateMedicalRecord(updatedRecord);
    }

    /**
     * @param firstName
     * @param lastName
     * @throws IOException
     */
    @Override
    public void deleteMedicalRecord(String firstName, String lastName) throws IOException {
        medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
    }
}
