package com.backend.parsers;

import com.backend.exceptions.JSONParsingException;
import com.backend.model.CreateOrderData;
import com.backend.model.FlightOfferData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightOrderManagementParser {

    private FlightOfferSearchParser flightOfferSearchParser;

    public FlightOrderManagementParser(FlightOfferSearchParser flightOfferSearchParser) {
        this.flightOfferSearchParser = flightOfferSearchParser;
    }

    public CreateOrderData parse(String json) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jRoot = null;
        try {
            jRoot = mapper.readTree(json);
        } catch (JsonProcessingException e) {

            throw new JSONParsingException("Ошибка JSON парсинга");
        }

        JsonNode jData = jRoot.path("data");
        JsonNode jTravelers = jData.path("travelers");

        CreateOrderData orderData = new CreateOrderData();
        List<CreateOrderData.Traveler> travelers = new ArrayList<>();

        for (int i = 0; i < jTravelers.size(); i++) {

            CreateOrderData.Traveler traveler = new CreateOrderData.Traveler();
            CreateOrderData.Traveler.Document document = new CreateOrderData.Traveler.Document();

            JsonNode jTraveler = jTravelers.get(i);
            String id = String.valueOf(jTraveler.get("id"));
            traveler.setId(id.substring(1, id.length() - 1));
            String gender = String.valueOf(jTraveler.get("gender"));
            traveler.setGender(gender.substring(1, gender.length() - 1));

            JsonNode jName = jTraveler.path("name");
            String firstName = String.valueOf(jName.get("firstName"));
            traveler.setFirstName(firstName.substring(1, firstName.length() - 1));
            String lastName = String.valueOf(jName.get("lastName"));
            traveler.setLastName(lastName.substring(1, lastName.length() - 1));

            JsonNode jDocuments = jTraveler.path("documents");
            JsonNode jDocument = jDocuments.get(0);
            String number = String.valueOf(jDocument.get("number"));
            document.setNumber(number.substring(1, number.length() - 1));
            String expiryDate = String.valueOf(jDocument.get("expiryDate"));
            document.setExpiryDate(expiryDate.substring(1, expiryDate.length() - 1));
            String issuanceCountry = String.valueOf(jDocument.get("issuanceCountry"));
            document.setIssuanceCountry(issuanceCountry.substring(1, issuanceCountry.length() - 1));
            String nationality = String.valueOf(jDocument.get("nationality"));
            document.setNationality(nationality.substring(1, nationality.length() - 1));
            String documentType = String.valueOf(jDocument.get("documentType"));
            document.setDocumentType(documentType.substring(1, documentType.length() - 1));
            String holder = String.valueOf(jDocument.get("holder"));

            document.setHolder(Boolean.valueOf(holder));
            traveler.setDocument(document);

            JsonNode jContact = jTraveler.path("contact");
            JsonNode jPhones = jContact.path("phones");
            JsonNode jPhone = jPhones.get(0);
            String countryCallingCode = String.valueOf(jPhone.get("countryCallingCode"));
            String number1 = String.valueOf(jPhone.get("number"));
            traveler.setPhone(countryCallingCode.substring(1, countryCallingCode.length() - 1)
                    + number1.substring(1, number1.length() - 1));
            String emailAddress = String.valueOf(jContact.get("emailAddress"));
            traveler.setEmailAddress(emailAddress.substring(1, emailAddress.length() - 1));

            travelers.add(traveler);
        }
        orderData.setTravelers(travelers);
        System.out.println(orderData);

        JsonNode jFlightOffers = jData.path("flightOffers");

        FlightOfferData flightOfferData = new FlightOfferData();

        JsonNode jFlightOffer = jFlightOffers.get(0);

        String id = String.valueOf(jFlightOffer.get("id"));
        flightOfferData.setId(id.substring(1, id.length() - 1));

        JsonNode jItineraries = jFlightOffer.path("itineraries");
        List<FlightOfferData.Itinerarie> itinerarieList = new ArrayList<>();

        for (int j = 0; j < jItineraries.size(); j++) {
            JsonNode segments = jItineraries.get(j).path("segments");

            List<FlightOfferData.Itinerarie.Segment> segmentList = new ArrayList<>();

            for (int k = 0; k < segments.size(); k++) {
                FlightOfferData.Itinerarie.Segment segment = new FlightOfferData.Itinerarie.Segment();
                FlightOfferData.Itinerarie.Segment.Departure departure = new FlightOfferData.Itinerarie.Segment.Departure();
                FlightOfferData.Itinerarie.Segment.Arrival arrival = new FlightOfferData.Itinerarie.Segment.Arrival();
                JsonNode jSegment = segments.get(k);
                JsonNode jDeparture = jSegment.path("departure");
                JsonNode jArrival = jSegment.path("arrival");
                String iataCodeDeparture = String.valueOf(jDeparture.get("iataCode"));
                departure.setIataCode(iataCodeDeparture.substring(1, iataCodeDeparture.length() - 1));

                String terminalDeparture = String.valueOf(jDeparture.get("terminal"));
                if (!terminalDeparture.equals("null")) {
                    departure.setTerminal(terminalDeparture.substring(1, terminalDeparture.length() - 1));
                }

                String atDeparture = String.valueOf(jDeparture.get("at"));
                departure.setAt(atDeparture.substring(1, atDeparture.length() - 1));

                String iataCodeArrival = String.valueOf(jArrival.get("iataCode"));
                arrival.setIataCode(iataCodeArrival.substring(1, iataCodeArrival.length() - 1));

                String terminalArrival = String.valueOf(jArrival.get("terminal"));
                if (!terminalArrival.equals("null")) {
                    arrival.setTerminal(terminalArrival.substring(1, terminalArrival.length() - 1));
                }

                String atArrival = String.valueOf(jArrival.get("at"));
                arrival.setAt(atArrival.substring(1, atArrival.length() - 1));

                segment.setArrival(arrival);
                segment.setDeparture(departure);
                segmentList.add(segment);
            }
            FlightOfferData.Itinerarie itinerarie = new FlightOfferData.Itinerarie();
            itinerarie.setSegments(segmentList);
            itinerarieList.add(itinerarie);

        }

        JsonNode jPrice = jFlightOffer.path("price");
        FlightOfferData.Price price = new FlightOfferData.Price();

        String currency = String.valueOf(jPrice.get("currency"));
        price.setCurrency(currency.substring(1, currency.length() - 1));

        String total = String.valueOf(jPrice.get("total"));
        price.setTotal(total.substring(1, total.length() - 1));


        flightOfferData.setItineraries(itinerarieList);
        flightOfferData.setPrice(price);

        orderData.setFlightOfferData(flightOfferData);

        System.out.println(orderData);

        return orderData;
    }


}
