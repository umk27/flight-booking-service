package com.backend.controller;

import com.backend.model.FlightOfferData;
import com.backend.services.FlightBookingService;
import com.backend.travelpayouts.TravelPayoutsClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("backend")
public class BackendController {

    private final TravelPayoutsClient travelPayoutsClient;

    private final FlightBookingService flightBookingService;

    public BackendController(TravelPayoutsClient travelPayoutsClient, FlightBookingService flightBookingService) {
        this.travelPayoutsClient = travelPayoutsClient;
        this.flightBookingService = flightBookingService;
    }


    @GetMapping("getIATACode")
    public String getIATACode(@RequestParam(name = "originLocation") String originLocation,
                              @RequestParam(name = "destinationLocation") String destinationLocation) {
        String q = "Из%20" + originLocation + "%20в%20" + destinationLocation;
        System.out.println(q);
        String str = travelPayoutsClient.getIATACode(originLocation, destinationLocation);
        return str;
    }


    @GetMapping("flightOfferSearch")
    public List<FlightOfferData> flightOfferSearch(@RequestParam(name = "originLocationCode") String originLocationCode,
                                                   @RequestParam(name = "destinationLocationCode") String destinationLocationCode,
                                                   @RequestParam(name = "departureDate") String departureDate,
                                                   @RequestParam(name = "adults") int adults, @RequestParam(name = "max") int max) {

        return flightBookingService.flightOfferSearch(originLocationCode, destinationLocationCode,departureDate,
                adults, max);

    }


    @GetMapping("flightOfferPrice")
    public FlightOfferData flightOfferPrice(@RequestBody FlightOfferData flightOfferData){
        return flightBookingService.flightOfferPrice(flightOfferData);
    }
}
