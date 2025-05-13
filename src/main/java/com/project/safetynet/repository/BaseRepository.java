package com.project.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * This is the base class for all the repository impl.
 *
 */

@Repository
public abstract class BaseRepository {
    protected String jsonFilePath;
    public final ObjectMapper objectMapper;

    public BaseRepository(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Map<String, List<?>> getAllData() throws IOException {
        return objectMapper.readValue(new File(jsonFilePath),
                new TypeReference<Map<String, List<?>>>() {});
    }
}
