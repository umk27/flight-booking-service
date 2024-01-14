package com.backend.repositories;

import com.backend.amadeus.AmadeusClient;
import com.backend.model.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "authRepo")
public class AuthorizationRepository {

    private final AmadeusClient amadeusClient;


    public AuthorizationRepository(AmadeusClient amadeusClient) {
        this.amadeusClient = amadeusClient;
    }

    @Cacheable
    public String authorization() {

        String json = amadeusClient.authorization(new UserData("Z4L6spaJqpkTDul6qsWPt08FUvZYxYFh", "u0B0EmZ5Xs9qjBAm",
                "client_credentials"));

        ObjectNode objectNode;
        try {
            objectNode = new ObjectMapper().readValue(json, ObjectNode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String token = String.valueOf(objectNode.get("access_token"));
        token = "Bearer " + token.substring(1, token.length() - 1);
        return token;
    }
}
