package com.backend.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class FlightCreateOrderParser {

    public String parse(String json) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jRoot = null;
        try {
            jRoot = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String id = String.valueOf(jRoot.path("data").get("id"));
        return id.substring(1, id.length() - 1);
    }
}
