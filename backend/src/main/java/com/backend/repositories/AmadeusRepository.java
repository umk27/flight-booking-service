package com.backend.repositories;

import com.backend.amadeus.AmadeusClient;
import com.backend.model.FlightOfferData;
import com.backend.parsers.FlightOfferSearchParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmadeusRepository {

    private final AmadeusClient amadeusClient;

    private final FlightOfferSearchParser parser;

    public AmadeusRepository(AmadeusClient amadeusClient, FlightOfferSearchParser parser) {
        this.amadeusClient = amadeusClient;
        this.parser = parser;
    }


    public List<FlightOfferData> flightOffersSearch(String authorization, String originLocationCode, String destinationLocationCode,
                                                    String departureDate, int adults, int max){

        String json = amadeusClient.flightOffersSearch(authorization, originLocationCode, destinationLocationCode,
                departureDate, adults, max);

        return parser.parse(json);
    }

    public FlightOfferData flightOfferPrice(String token, FlightOfferData flightOfferData){
        String json = amadeusClient.flightOffersPrice(token, flightOfferData.getFlightOffer());
    }
}
