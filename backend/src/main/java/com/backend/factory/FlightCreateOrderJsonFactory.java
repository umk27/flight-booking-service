package com.backend.factory;

import com.backend.model.CreateOrderData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;


@Service
public class FlightCreateOrderJsonFactory {

    public JsonNode build(CreateOrderData createOrderData) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.createObjectNode();
        ObjectNode jRoot = (ObjectNode) root;
        jRoot = jRoot.putObject("data");
        jRoot.put("type", "flight-order");

        ArrayNode jFlightOffers = mapper.createArrayNode();
        jFlightOffers.add(createOrderData.getFlightOffer());

        jRoot.putArray("flightOffers").addAll(jFlightOffers);

        ArrayNode jTravelers = mapper.createArrayNode();

        for (int i = 0; i < createOrderData.getTravelers().size(); i++) {

            CreateOrderData.Traveler person = createOrderData.getTravelers().get(i);

            JsonNode traveler = mapper.createObjectNode();
            ObjectNode jTraveler = (ObjectNode) traveler;

            jTraveler.put("id", person.getId())
                    .put("dateOfBirth", person.getDateOfBirth());

            jTraveler.putObject("name").put("firstName", person.getFirstName())
                    .put("lastName", person.getLastName());
            jTraveler.put("gender", person.getGender());

            ObjectNode jPhone = mapper.createObjectNode();
            jPhone.put("deviceType", "MOBILE")
                    .put("countryCallingCode", "8")
                    .put("number", person.getPhone());
            ArrayNode jPhones = mapper.createArrayNode();
            jPhones.add(jPhone);

            jTraveler.putObject("contact").put("emailAddress", person.getEmailAddress())
                    .putArray("phones").addAll(jPhones);

            ObjectNode jDocument = mapper.createObjectNode();
            jDocument.put("documentType", person.getDocument().getDocumentType());
            jDocument.put("birthPlace", person.getDocument().getBirthPlace());
            jDocument.put("issuanceLocation", person.getDocument().getIssuanceLocation());
            jDocument.put("issuanceDate", person.getDocument().getIssuanceDate());
            jDocument.put("number", person.getDocument().getNumber());
            jDocument.put("expiryDate", person.getDocument().getExpiryDate());
            jDocument.put("issuanceCountry", person.getDocument().getIssuanceCountry());
            jDocument.put("validityCountry", person.getDocument().getValidityCountry());
            jDocument.put("nationality", person.getDocument().getNationality());
            jDocument.put("holder", person.getDocument().isHolder());
            ArrayNode jDocuments = mapper.createArrayNode();
            jDocuments.add(jDocument);

            jTraveler.putArray("documents").addAll(jDocuments);
            jTravelers.add(jTraveler);
        }
        jRoot.putArray("travelers").addAll(jTravelers);
        return root;
    }
}
