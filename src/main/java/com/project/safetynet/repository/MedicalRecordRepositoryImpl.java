package com.project.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MedicalRecordRepositoryImpl extends BaseRepository implements MedicalRecordRepository {

    public MedicalRecordRepositoryImpl(ObjectMapper objectMapper, @Value("${json.file.path}" ) String jsonFilePath) {
        super(objectMapper);
        this.jsonFilePath = jsonFilePath;
    }
    @Override
    public List<MedicalRecord> readMedicalRecordsFromJSON() throws IOException {
        Map<String, List<?>> allData = getAllData();
        return objectMapper.convertValue(allData.get("medicalrecords"),
                new TypeReference<List<MedicalRecord>>() {});
    }

    @Override
    public void addMedicalRecord(MedicalRecord record) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<MedicalRecord> records = objectMapper.convertValue(allData.get("medicalrecords"), new TypeReference<List<MedicalRecord>>() {});
        records.add(record);
        allData.put("medicalrecords", records);
        objectMapper.writeValue(new File(jsonFilePath), allData);
    }

    @Override
    public void updateMedicalRecord(MedicalRecord updatedRecord) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<MedicalRecord> records = objectMapper.convertValue(allData.get("medicalrecords"), new TypeReference<List<MedicalRecord>>() {});
        Optional<MedicalRecord> existingMedicalRecord = records.stream()
                .filter(p -> p.getFirstName().equals(updatedRecord.getFirstName())
                        && p.getLastName().equals(updatedRecord.getLastName()))
                .findFirst();
        if (existingMedicalRecord.isPresent()) {
            MedicalRecord record = existingMedicalRecord.get();
            record.setBirthdate(updatedRecord.getBirthdate());
            record.setAllergies(updatedRecord.getAllergies());
            record.setMedications(updatedRecord.getMedications());
            allData.put("medicalrecords", records);
            objectMapper.writeValue(new File(jsonFilePath), allData);
        } else{
            throw new RuntimeException("MedicalRecord not found");
        }
    }

    @Override
    public void deleteMedicalRecord(String firstName, String lastName) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<MedicalRecord> records = objectMapper.convertValue(allData.get("medicalrecords"), new TypeReference<List<MedicalRecord>>() {});
        Optional<MedicalRecord> existingMedicalRecord = records.stream()
                .filter(p -> p.getFirstName().equals(firstName)
                        && p.getLastName().equals(lastName))
                .findFirst();
        if (existingMedicalRecord.isPresent()) {
            records.remove(existingMedicalRecord.get());
            allData.put("medicalrecords", records);
            objectMapper.writeValue(new File(jsonFilePath), allData);
        } else{
            throw new RuntimeException("MedicalRecord not found");
        }
    }
}
