package com.backend.repositories;

import com.backend.amadeus.AmadeusClient;
import com.backend.model.FlightOfferData;
import com.backend.parsers.FlightOfferPriceParser;
import com.backend.parsers.FlightOfferSearchParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmadeusRepository {

    private final AmadeusClient amadeusClient;

    private final FlightOfferSearchParser flightOfferSearchParser;

    private final FlightOfferPriceParser flightOfferPriceParser;

    public AmadeusRepository(AmadeusClient amadeusClient, FlightOfferSearchParser parser, FlightOfferPriceParser flightOfferPriceParser) {
        this.amadeusClient = amadeusClient;
        this.flightOfferSearchParser = parser;
        this.flightOfferPriceParser = flightOfferPriceParser;
    }


    public List<FlightOfferData> flightOffersSearch(String authorization, String originLocationCode, String destinationLocationCode,
                                                    String departureDate, int adults, int max) {

        String json = amadeusClient.flightOffersSearch(authorization, originLocationCode, destinationLocationCode,
                departureDate, adults, max);

        return flightOfferSearchParser.parse(json);
    }

    public FlightOfferData flightOfferPrice(String token, FlightOfferData flightOfferData) {
        String json = amadeusClient.flightOffersPrice(token, flightOfferData.getFlightOffer());
        String total = flightOfferPriceParser.parse(json);
        if (total != null){
            FlightOfferData.Price price1 = flightOfferData.getPrice();
            price1.setTotal(total);
            flightOfferData.setPrice(price1);
        }
        return flightOfferData;
    }
}
