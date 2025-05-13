package com.project.safetynet.repository;

import com.project.safetynet.model.MedicalRecord;

import java.io.IOException;
import java.util.List;

public interface MedicalRecordRepository {
    List<MedicalRecord> readMedicalRecordsFromJSON() throws IOException;

    void addMedicalRecord(MedicalRecord record) throws IOException;

    void updateMedicalRecord(MedicalRecord updatedRecord) throws IOException;

    void deleteMedicalRecord(String firstName, String lastName) throws IOException;
}
