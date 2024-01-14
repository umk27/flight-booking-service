package com.backend.controller;

import com.backend.travelpayouts.TravelPayoutsClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("backend")
public class BackendController {

    private final TravelPayoutsClient travelPayoutsClient;

    public BackendController(TravelPayoutsClient travelPayoutsClient) {
        this.travelPayoutsClient = travelPayoutsClient;
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
    public String flightOfferSearch(@RequestParam(name = "originLocationCode") String originLocationCode,
                                    @RequestParam(name = "destinationLocationCode") String destinationLocationCode,
                                    @RequestParam(name = "departureDate") String departureDate,
                                    @RequestParam(name = "adults") int adults, @RequestParam(name = "max") int max) {
        System.out.println(originLocationCode);
        System.out.println(destinationLocationCode);
        System.out.println(departureDate);
        System.out.println(adults);
        System.out.println(max);

        return "Ok";

    }
}
