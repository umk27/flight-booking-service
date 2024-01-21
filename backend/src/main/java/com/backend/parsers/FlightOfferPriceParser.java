package com.backend.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FlightOfferPriceParser {

    public String parse(String json){

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jRoot = null;
        try {
            jRoot = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (jRoot.has("warnings")) {
            JsonNode jWarnings = jRoot.path("warnings");
            for (int i = 0; i < jWarnings.size(); i++) {
                JsonNode jWarning = jWarnings.get(i);

                String detail = String.valueOf(jWarning.get("detail"));
                if (detail.substring(1, detail.length() - 1).equals("Actual price and/or fare basis for " +
                        "some passengers is different from requested ones")) {
                    JsonNode jPrice = jRoot.path("data").path("flightOffers").get(0).path("price");
                    String price = String.valueOf(jPrice.get("total"));
                    return price;
                }
            }

        }

        return null;
    }
}
