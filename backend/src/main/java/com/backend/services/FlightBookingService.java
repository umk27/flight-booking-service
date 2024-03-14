package com.backend.services;

import com.backend.model.CreateOrderData;
import com.backend.model.FlightOfferData;
import com.backend.repositories.AuthorizationRepository;
import com.backend.repositories.AmadeusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightBookingService {

    private final AuthorizationRepository authorizationRepository;

    private final AmadeusRepository amadeusRepository;

    public FlightBookingService(AuthorizationRepository authorizationRepository, AmadeusRepository amadeusRepository) {
        this.authorizationRepository = authorizationRepository;
        this.amadeusRepository = amadeusRepository;
    }


    public List<FlightOfferData> flightOfferSearch(String originLocationCode, String destinationLocationCode,
                                                   String departureDate, int adults, int max) {

        String token = authorizationRepository.authorization();

        return amadeusRepository.flightOffersSearch(token, originLocationCode, destinationLocationCode,
                departureDate, adults, max);

    }

    public List<FlightOfferData> flightOfferSearch(String originLocationCode, String destinationLocationCode,
                                                   String returnDate, String departureDate, int adults, int max) {

        String token = authorizationRepository.authorization();

        return amadeusRepository.flightOffersSearch(token, originLocationCode, destinationLocationCode,
                departureDate, returnDate, adults, max);

    }

    public FlightOfferData flightOfferPrice(FlightOfferData flightOfferData) {

        String token = authorizationRepository.authorization();

        return amadeusRepository.flightOfferPrice(token, flightOfferData);

    }

    public String flightCreateOrder(CreateOrderData createOrderData) {

        String token = authorizationRepository.authorization();

        return amadeusRepository.flightCreateOrder(token, createOrderData);

    }

    public CreateOrderData flightOrderManagement(String id) {
        String token = authorizationRepository.authorization();
       return amadeusRepository.flightOrderManagement(token, id);
    }

    public void flightDeleteOrder(String id) {

        String token = authorizationRepository.authorization();

        amadeusRepository.flightDeleteOrder(token, id);

    }
}
