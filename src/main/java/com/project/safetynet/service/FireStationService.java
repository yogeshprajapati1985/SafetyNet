package com.project.safetynet.service;

import com.project.safetynet.dto.FireResponseDTO;
import com.project.safetynet.dto.FireStationResponseDTO;
import com.project.safetynet.dto.PersonDTO;
import com.project.safetynet.model.FireStation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service interface for managing fire station data.
 */
public interface FireStationService {
    Map<String, Set<PersonDTO>> getHouseholdInfo(List<Integer> fireStationNumbers) throws IOException;

    FireResponseDTO getFireData(String address) throws IOException;

    List<String> getPhoneNumbers(int station) throws IOException;

    FireStationResponseDTO getFireStationData(int stationNumber) throws IOException;
    void addFireStation(FireStation fireStation) throws IOException;
    void updateFireStation(FireStation updatedFireStation) throws IOException;
    void deleteFireStation(int station) throws IOException;
}
