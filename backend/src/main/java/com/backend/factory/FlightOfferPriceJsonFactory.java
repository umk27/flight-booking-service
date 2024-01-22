package com.backend.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class FlightOfferPriceJsonFactory {


    public JsonNode build(JsonNode flightOffer) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jRoot = mapper.createObjectNode();

        ObjectNode root = (ObjectNode) jRoot;
        root = root.putObject("data");
        root.put("type", "flight-offers-pricing");

        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(flightOffer);

        root.putArray("flightOffers").addAll(arrayNode);

       // System.out.println(jRoot.toString());

        return jRoot;
    }
}
