package com.userinterface.feignclient;

import com.userinterface.model.CreateOrderData;
import com.userinterface.model.FlightOfferData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "amadeus-client")
public interface BackendClient {

    @GetMapping("backend/getIATACode")
    String getIATACode(@RequestParam(name = "originLocation") String originLocation,
                       @RequestParam(name = "destinationLocation") String destinationLocation);

    @GetMapping("backend/1/flightOfferSearch")
    List<FlightOfferData> flightOfferSearch(@RequestParam(name = "originLocationCode") String originLocationCode,
                                                   @RequestParam(name = "destinationLocationCode") String destinationLocationCode,
                                                   @RequestParam(name = "departureDate") String departureDate,
                                                   @RequestParam(name = "returnDate") String returnDate,
                                                   @RequestParam(name = "adults") int adults, @RequestParam(name = "max") int max);


    @PostMapping("backend/flightOfferPrice")
    FlightOfferData flightOfferPrice(@RequestBody FlightOfferData flightOfferData);

    @PostMapping("backend/flightCreateOrder")
    String flightCreateOrder(@RequestBody CreateOrderData createOrderData);

    @DeleteMapping("backend/flightDeleteOrder/{id}")
    void flightDeleteOrder(@PathVariable(name = "id") String id);

    @GetMapping("backend/flightOrderManagement/{id}")
    CreateOrderData flightOrderManagement(@PathVariable(name = "id") String id);

}