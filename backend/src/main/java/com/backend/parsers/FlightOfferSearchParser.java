package com.backend.parsers;

import com.backend.exceptions.JSONParsingException;
import com.backend.model.FlightOfferData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FlightOfferSearchParser {


    public List<FlightOfferData> parse(String json) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jRoot = null;
        try {
            jRoot = mapper.readTree(json);
        } catch (JsonProcessingException e) {

            throw new JSONParsingException("Ошибка JSON парсинга");
        }

        JsonNode jData = jRoot.path("data");


        List<FlightOfferData> flightOfferDataList = new ArrayList<>();

        for (int i = 0; i < jData.size(); i++) {
            JsonNode jData1 = jData.get(i);

            FlightOfferData flightOfferData = new FlightOfferData();

            String id = String.valueOf(jData1.get("id"));
            flightOfferData.setId(id.substring(1, id.length() - 1));
            flightOfferData.setFlightOffer(jData1);

            JsonNode jItineraries = jData1.path("itineraries");
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


            JsonNode jPrice = jData1.path("price");
            FlightOfferData.Price price = new FlightOfferData.Price();

            String currency = String.valueOf(jPrice.get("currency"));
            price.setCurrency(currency.substring(1, currency.length() - 1));

            String total = String.valueOf(jPrice.get("total"));
            price.setTotal(total.substring(1, total.length() - 1));


            flightOfferData.setItineraries(itinerarieList);
            flightOfferData.setPrice(price);
            flightOfferDataList.add(flightOfferData);

        }
        return flightOfferDataList;
    }
}
