package com.backend.controller;

import com.backend.factory.FlightCreateOrderJsonFactory;
import com.backend.model.CreateOrderData;
import com.backend.model.FlightOfferData;
import com.backend.services.FlightBookingService;
import com.backend.travelpayouts.TravelPayoutsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("backend")
public class BackendController {

    private final TravelPayoutsClient travelPayoutsClient;

    private final FlightBookingService flightBookingService;

    private final FlightCreateOrderJsonFactory flightCreateOrderJsonFactory;

    public BackendController(TravelPayoutsClient travelPayoutsClient, FlightBookingService flightBookingService, FlightCreateOrderJsonFactory flightCreateOrderJsonFactory) {
        this.travelPayoutsClient = travelPayoutsClient;
        this.flightBookingService = flightBookingService;
        this.flightCreateOrderJsonFactory = flightCreateOrderJsonFactory;
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

        return flightBookingService.flightOfferSearch(originLocationCode, destinationLocationCode, departureDate,
                adults, max);

    }

    @GetMapping("/1/flightOfferSearch")
    public List<FlightOfferData> flightOfferSearch(@RequestParam(name = "originLocationCode") String originLocationCode,
                                                   @RequestParam(name = "destinationLocationCode") String destinationLocationCode,
                                                   @RequestParam(name = "departureDate") String departureDate,
                                                   @RequestParam(name = "returnDate") String returnDate,
                                                   @RequestParam(name = "adults") int adults, @RequestParam(name = "max") int max) {

        return flightBookingService.flightOfferSearch(originLocationCode, destinationLocationCode, departureDate,
               returnDate, adults, max);

    }


    @PostMapping("flightOfferPrice")
    public FlightOfferData flightOfferPrice(@RequestBody FlightOfferData flightOfferData) {
        return flightBookingService.flightOfferPrice(flightOfferData);
    }


    @PostMapping("flightCreateOrder")
    public String flightCreateOrder(@RequestBody CreateOrderData createOrderData) {
        ;
        return flightBookingService.flightCreateOrder(createOrderData);
    }

    @GetMapping("flightOrderManagement/{id}")
    public CreateOrderData flightOrderManagement(@PathVariable(name = "id") String id) {
        return flightBookingService.flightOrderManagement(id);
    }

    @DeleteMapping("flightDeleteOrder/{id}")
    public void flightDeleteOrder(@PathVariable(name = "id") String id) {

        flightBookingService.flightDeleteOrder(id);
    }
}
