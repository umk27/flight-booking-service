package com.backend.repositories;

import com.backend.amadeus.AmadeusClient;
import com.backend.factory.FlightOfferPriceJsonFactory;
import com.backend.model.FlightOfferData;
import com.backend.parsers.FlightOfferPriceParser;
import com.backend.parsers.FlightOfferSearchParser;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmadeusRepository {

    private final AmadeusClient amadeusClient;

    private final FlightOfferSearchParser flightOfferSearchParser;

    private final FlightOfferPriceParser flightOfferPriceParser;

    private final FlightOfferPriceJsonFactory flightOfferPriceJsonFactory;

    public AmadeusRepository(AmadeusClient amadeusClient, FlightOfferSearchParser parser, FlightOfferPriceParser flightOfferPriceParser, FlightOfferPriceJsonFactory flightOfferPriceJsonFactory) {
        this.amadeusClient = amadeusClient;
        this.flightOfferSearchParser = parser;
        this.flightOfferPriceParser = flightOfferPriceParser;
        this.flightOfferPriceJsonFactory = flightOfferPriceJsonFactory;
    }


    public List<FlightOfferData> flightOffersSearch(String authorization, String originLocationCode, String destinationLocationCode,
                                                    String departureDate, int adults, int max) {

        String json = amadeusClient.flightOffersSearch(authorization, originLocationCode, destinationLocationCode,
                departureDate, adults, max);

        return flightOfferSearchParser.parse(json);
    }

    public FlightOfferData flightOfferPrice(String token, FlightOfferData flightOfferData) {
        JsonNode flightOfferRequest = flightOfferPriceJsonFactory.build(flightOfferData.getFlightOffer());
        String str = flightOfferRequest.toString();
        System.out.println(str);
        String json = amadeusClient.flightOffersPrice(token, str);
        FlightOfferData flightOfferPriceData = flightOfferPriceParser.parse(json);
        if (flightOfferPriceData.getPrice() != null) {
            FlightOfferData.Price price = flightOfferData.getPrice();
            price.setTotal(flightOfferPriceData.getPrice().getTotal());
            flightOfferData.setPrice(price);
        }
        return flightOfferData;
    }
}
