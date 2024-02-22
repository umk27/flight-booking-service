package com.backend.repositories;

import com.backend.amadeus.AmadeusClient;
import com.backend.factory.FlightCreateOrderJsonFactory;
import com.backend.factory.FlightOfferPriceJsonFactory;
import com.backend.model.CreateOrderData;
import com.backend.model.FlightOfferData;
import com.backend.parsers.FlightCreateOrderParser;
import com.backend.parsers.FlightOfferPriceParser;
import com.backend.parsers.FlightOfferSearchParser;
import com.backend.parsers.FlightOrderManagementParser;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmadeusRepository {

    private final AmadeusClient amadeusClient;

    private final FlightOfferSearchParser flightOfferSearchParser;

    private final FlightOfferPriceParser flightOfferPriceParser;

    private final FlightCreateOrderParser flightCreateOrderParser;

    private final FlightOrderManagementParser flightOrderManagementParser;

    private final FlightOfferPriceJsonFactory flightOfferPriceJsonFactory;

    private final FlightCreateOrderJsonFactory flightCreateOrderJsonFactory;

    public AmadeusRepository(AmadeusClient amadeusClient, FlightOfferSearchParser parser, FlightOfferPriceParser flightOfferPriceParser, FlightCreateOrderParser flightCreateOrderParser, FlightOrderManagementParser flightOrderManagementParser, FlightOfferPriceJsonFactory flightOfferPriceJsonFactory, FlightCreateOrderJsonFactory flightCreateOrderJsonFactory) {
        this.amadeusClient = amadeusClient;
        this.flightOfferSearchParser = parser;
        this.flightOfferPriceParser = flightOfferPriceParser;
        this.flightCreateOrderParser = flightCreateOrderParser;
        this.flightOrderManagementParser = flightOrderManagementParser;
        this.flightOfferPriceJsonFactory = flightOfferPriceJsonFactory;
        this.flightCreateOrderJsonFactory = flightCreateOrderJsonFactory;
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

    public String flightCreateOrder(String token, CreateOrderData createOrderData) {
        JsonNode createOrderRequest = flightCreateOrderJsonFactory.build(createOrderData);
        String str = createOrderRequest.toString();
        String json = amadeusClient.flightCreateOrders(token, str);
        return flightCreateOrderParser.parse(json);
    }

    public void flightOrderManagement(String token, String id) {
        String json = amadeusClient.flightOrderManagement(token, id);
        flightOrderManagementParser.parse(json);
    }

    public void flightDeleteOrder(String token, String id) {
        amadeusClient.flightDeleteOrders(token, id);
    }
}
