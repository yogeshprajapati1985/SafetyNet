package com.project.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.safetynet.model.FireStation;
import com.project.safetynet.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This interface defines the contract for the FireStationRepository.
 * It is responsible for managing the data related to fire stations.
 * The implementation details are not provided in this snippet.
 */

@Repository
public class FireStationRepositoryImpl extends BaseRepository implements FireStationRepository
{
    public FireStationRepositoryImpl(ObjectMapper objectMapper, @Value("${json.file.path}" ) String jsonFilePath) {
        super(objectMapper);
        this.jsonFilePath = jsonFilePath;
    }

    @Override
    public List<FireStation> readFireStationsFromJSON() throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<FireStation> fireStations = objectMapper.convertValue(allData.get("firestations"),
                new TypeReference<List<FireStation>>() {});
        return fireStations;
    }

    @Override
    public void addFireStation(FireStation fireStation) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<FireStation> fireStations = (List<FireStation>) allData.get("firestations");
        fireStations.add(fireStation);
        allData.put("firestations", fireStations);
        objectMapper.writeValue(new File(jsonFilePath), allData);
    }

    @Override
    public void updateFireStation(FireStation updatedFireStation) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<FireStation> firestations = objectMapper.convertValue(
                allData.get("firestations"), new TypeReference<List<FireStation>>() {});
        Optional<FireStation> existingFireStation = firestations.stream()
                .filter(f -> f.getStation() == updatedFireStation.getStation())
                .findFirst();
        if (existingFireStation.isPresent()) {
            FireStation station = existingFireStation.get();
            station.setAddress(updatedFireStation.getAddress());
            allData.put("firestations", firestations);
            objectMapper.writeValue(new File(jsonFilePath), allData);
        } else{
            throw new RuntimeException("Fire Station not found");
        }
    }

    @Override
    public void deleteFireStation(int station) throws IOException {
        Map<String, List<?>> allData = getAllData();
        List<FireStation> firestations = objectMapper.convertValue(
                allData.get("firestations"), new TypeReference<List<FireStation>>() {});
        Optional<FireStation> existingFireStation = firestations.stream()
                .filter(f -> f.getStation() == station)
                .findFirst();
        if (existingFireStation.isPresent()) {
            firestations.remove(existingFireStation.get());
            allData.put("firestations", firestations);
            objectMapper.writeValue(new File(jsonFilePath), allData);
        } else{
            throw new RuntimeException("Fire Station not found");
        }
    }
}
