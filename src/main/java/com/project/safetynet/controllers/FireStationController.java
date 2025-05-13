package com.project.safetynet.controllers;

import com.project.safetynet.dto.FireResponseDTO;
import com.project.safetynet.dto.FireStationResponseDTO;
import com.project.safetynet.dto.PersonDTO;
import com.project.safetynet.model.FireStation;
import com.project.safetynet.service.FireStationService;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Validated
@RequiredArgsConstructor
public class FireStationController {
    private final FireStationService fireStationService;

    /**
     * This method retrieves a map of household information based on the provided fire station numbers.
     *
     * @param stations the list of fire station numbers
     * @return a map where the key is the address and the value is a set of PersonDTO objects
     */
    @GetMapping(path = "/flood/stations")
    public Map<String, Set<PersonDTO>> getHouseholds(@RequestParam List<Integer> stations) throws IOException {
        return fireStationService.getHouseholdInfo(stations);
    }

    /**
     * This method retrieves fire data for the given address.
     *
     * @param address the address to get fire data for
     * @return a FireResponseDTO object containing fire data for the specified address
     */
    @GetMapping(path = "/fire")
    public FireResponseDTO getFireData(@RequestParam String address) throws IOException {
        return fireStationService.getFireData(address);
    }

    /**
     * This method should return a list of phone numbers of each person within the fire station’s jurisdiction.We’ll
     * use this to send emergency text messages to specific households.
     *
     * @param firestation the fire station number
     * @return a List<String> list of phone numbers
     */
    @GetMapping(path = "/phoneAlert")
    public List<String> getPhoneNumbers(@RequestParam int firestation) throws IOException {
        return fireStationService.getPhoneNumbers(firestation);
    }

    /**
     * This method should return a list of people serviced by the corresponding fire station. So if station number = 1,
     * it should return the people serviced by station number 1. The list of people should include these specific
     * pieces of information: first name, last name, address, phone number. As well, it should provide a
     * summary of the number of adults in the service area and the number of children (anyone aged 18 or
     * younger).
     *
     * @param stationNumber the fire station number
     * @return a FireStationResponseDTO object containing the fire station number, a list of PersonDTO objects,
     * and the number of adults and children
     */

    @GetMapping(path = "/firestation")
    public FireStationResponseDTO getFireStationData(@RequestParam int stationNumber) throws IOException {
        return fireStationService.getFireStationData(stationNumber);
    }

    /**
     * This method adds a new fire station to the system.
     * @param fireStation
     * @throws IOException
     */
    @PostMapping(path = "/firestation")
    public void addFireStation(@RequestBody FireStation fireStation) throws IOException {
        fireStationService.addFireStation(fireStation);
    }

    /**
     * This method updates an existing fire station in the system.
     * @param updatedFireStation
     * @throws IOException
     */
    @PutMapping(path = "/firestation")
    public void updateFireStation(@RequestBody FireStation updatedFireStation) throws IOException {
        fireStationService.updateFireStation(updatedFireStation);
    }

    /**
     * This method deletes a fire station from the system based on the provided station number.
     *
     * @param station
     * @throws IOException
     */
    @DeleteMapping(path = "/firestation")
    public void deleteFireStation(@RequestParam int station) throws IOException {
        fireStationService.deleteFireStation(station);
    }
}
