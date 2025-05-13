package com.project.safetynet.repository;

import com.project.safetynet.model.FireStation;

import java.io.IOException;
import java.util.List;

/**
 * This interface defines the contract for the FireStationRepository.
 * It is responsible for managing the data related to fire stations.
 * The implementation details are not provided in this snippet.
 */
public interface FireStationRepository {
    List<FireStation> readFireStationsFromJSON() throws IOException;
    void addFireStation(FireStation fireStation) throws IOException;
    void updateFireStation(FireStation updatedFireStation) throws IOException;
    void deleteFireStation(int station) throws IOException;
}
