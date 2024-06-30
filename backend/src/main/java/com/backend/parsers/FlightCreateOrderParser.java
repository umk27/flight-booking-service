package com.backend.parsers;

import com.backend.exceptions.JSONParsingException;
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

            throw new JSONParsingException("Ошибка JSON парсинга");
        }

        String id = String.valueOf(jRoot.path("data").get("id"));
        return id.substring(1, id.length() - 1);
    }
}
