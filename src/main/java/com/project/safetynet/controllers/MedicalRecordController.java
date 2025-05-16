package com.project.safetynet.controllers;

import com.project.safetynet.dto.ChildAlertDTO;
import com.project.safetynet.model.MedicalRecord;
import com.project.safetynet.service.MedicalRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    /**
     * This method L should return a list of children (anyone under the age of 18) at that address. The list should
     * include the first and last name of each child, the child’s age, and a list of other persons living at that
     * address. If there are no children at the address, then this URL can return an empty string.
     *
     * @param address the address to get children information for
     * @return a ChildAlertDTO object containing the list of children and other persons living at the address
     */

    @GetMapping(path = "/childAlert")
    public ChildAlertDTO getChildren(@RequestParam @NotBlank (message = "address must not be blank.") String address) throws IOException {
        return medicalRecordService.getChildren(address);
    }

    /**
     * This method should add a new medical record to the system.
     *
     * @param record the medical record to be added
     * @throws IOException throws IOException if there is an error during the operation
     */
    @PostMapping(path = "/medicalRecord")
    public void addMedicalRecord(@RequestBody @Valid MedicalRecord record) throws IOException {
        medicalRecordService.addMedicalRecord(record);
    }

    /**
     * This method should update an existing medical record in the system.
     *
     * @param updatedMedicalRecord the medical record to be updated
     * @throws IOException throws IOException if there is an error during the operation
     */
    @PutMapping(path = "/medicalRecord")
    public void updateMedicalRecord(@RequestBody @Valid MedicalRecord updatedMedicalRecord) throws IOException {
        medicalRecordService.updateMedicalRecord(updatedMedicalRecord);
    }

    /**
     * This method should delete a medical record from the system based on the provided first and last name.
     *
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @throws IOException throws IOException if there is an error during the operation
     */
    @DeleteMapping(path = "/medicalRecord")
    public void deleteMedicalRecord(@RequestParam @NotBlank(message = "firstName is required.") String firstName,
                                    @RequestParam @NotBlank(message = "lastName is required.") String lastName) throws IOException {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
}
