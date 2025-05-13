package com.project.safetynet.service;

import com.project.safetynet.dto.ChildAlertDTO;
import com.project.safetynet.model.MedicalRecord;

import java.io.IOException;

public interface MedicalRecordService {
    ChildAlertDTO getChildren(String address) throws IOException;
    void addMedicalRecord(MedicalRecord record) throws IOException;

    void updateMedicalRecord(MedicalRecord updatedRecord) throws IOException;

    void deleteMedicalRecord(String firstName, String lastName) throws IOException;
}
