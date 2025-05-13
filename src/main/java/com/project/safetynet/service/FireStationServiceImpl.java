package com.project.safetynet.service;

import com.project.safetynet.dto.FireResponseDTO;
import com.project.safetynet.dto.FireStationResponseDTO;
import com.project.safetynet.dto.PersonDTO;
import com.project.safetynet.mapper.SafetyNetMapper;
import com.project.safetynet.model.FireStation;
import com.project.safetynet.model.MedicalRecord;
import com.project.safetynet.model.Person;
import com.project.safetynet.repository.FireStationRepository;
import com.project.safetynet.repository.MedicalRecordRepository;
import com.project.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing fire station data.
 */
@RequiredArgsConstructor
@Service
public class FireStationServiceImpl implements FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final SafetyNetMapper mapper;

    /**
     * This method retrieves household information for the given fire station numbers.
     *
     * @param fireStationNumbers the list of fire station numbers
     * @return a map where the key is the address and the value is a set of PersonDTO objects
     */
    @Override
    public Map<String, Set<PersonDTO>> getHouseholdInfo(List<Integer> fireStationNumbers) throws IOException {

        List<FireStation> filteredFireStations = fireStationRepository.readFireStationsFromJSON()
                .stream()
                .filter(fireStation -> fireStationNumbers.contains(fireStation.getStation()))
                .collect(Collectors.toList());

        List<Person> filteredPersons = personRepository.readPersonsFromJSON().stream()
                .filter(person -> filteredFireStations.stream()
                        .anyMatch(fireStation -> fireStation.getAddress().equals(person.getAddress())))
                .collect(Collectors.toList());

        return mapper.toHouseholdMedicalRecordDTOList(filteredPersons, medicalRecordRepository.readMedicalRecordsFromJSON());
    }

    /**
     * This method retrieves fire data for the given address.
     * @param address the address to get fire data for
     * @return null
     */
    @Override
    public FireResponseDTO getFireData(String address) throws IOException {

        List<Person> filteredPersons = personRepository.readPersonsFromJSON().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
        FireStation fireStation = fireStationRepository.readFireStationsFromJSON().stream()
                .filter(station -> station.getAddress().equals(address)).findFirst().orElse(null);
        return mapper.toFireResponseDTO(fireStation, filteredPersons, medicalRecordRepository.readMedicalRecordsFromJSON());
    }

    /**
     * This method should return a list of phone numbers of each person within the fire station’s jurisdiction.
     *
     * @param station the fire station number
     * @return a List<String> list of phone numbers
     */
    @Override
    public List<String> getPhoneNumbers(int station) throws IOException {

        List<String> phoneNumbers = new ArrayList<String>();

        List<FireStation> filteredFireStations = fireStationRepository.readFireStationsFromJSON()
                .stream()
                .filter(fireStation -> fireStation.getStation() == station)
                .collect(Collectors.toList());

        if (filteredFireStations != null && !filteredFireStations.isEmpty()) {
            phoneNumbers = personRepository.readPersonsFromJSON().stream()
                    .filter(person -> filteredFireStations.stream()
                            .anyMatch(fireStation -> fireStation.getAddress().equals(person.getAddress())))
                    .map(Person::getPhone)
                    .collect(Collectors.toList());

            // Remove duplicates
            phoneNumbers = phoneNumbers.stream().distinct().collect(Collectors.toList());
        }

        return phoneNumbers;
    }

    /**
     * @param stationNumber
     * @return
     */
    @Override
    public FireStationResponseDTO getFireStationData(int stationNumber) throws IOException {

        List<FireStation> filteredFireStations = fireStationRepository.readFireStationsFromJSON()
                .stream()
                .filter(fireStation -> fireStation.getStation() == stationNumber)
                .collect(Collectors.toList());

        List<Person> filteredPersons = personRepository.readPersonsFromJSON().stream()
                .filter(person -> filteredFireStations.stream()
                        .anyMatch(fireStation -> fireStation.getAddress().equals(person.getAddress())))
                .collect(Collectors.toList());

        List<MedicalRecord> medicalRecords = medicalRecordRepository.readMedicalRecordsFromJSON().stream()
                .filter(medicalRecord -> filteredPersons.stream()
                        .anyMatch(person -> person.getFirstName().equals(medicalRecord.getFirstName())
                                && person.getLastName().equals(medicalRecord.getLastName()))).toList();

        return mapper.toFireStationResponseDTO(filteredPersons, medicalRecords);
    }

    /**
     * @param fireStation
     * @throws Exception
     */
    @Override
    public void addFireStation(FireStation fireStation) throws IOException {
        fireStationRepository.addFireStation(fireStation);
    }

    /**
     * @param updatedFireStation
     * @throws Exception
     */
    @Override
    public void updateFireStation(FireStation updatedFireStation) throws IOException {
        fireStationRepository.updateFireStation(updatedFireStation);
    }

    /**
     * @param station
     * @throws Exception
     */
    @Override
    public void deleteFireStation(int station) throws IOException {
        fireStationRepository.deleteFireStation(station);
    }
}
