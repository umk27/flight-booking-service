package com.backend.amadeus;

import com.backend.model.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "amadeusClient", url = "https://test.api.amadeus.com")
public interface AmadeusClient {

    @PostMapping(value = "v1/security/oauth2/token", consumes = "application/x-www-form-urlencoded")
    String authorization(UserData userData);

    @GetMapping("v2/shopping/flight-offers")
    String flightOffersSearch(@RequestHeader(name = "Authorization") String authorization,
                              @RequestParam(name = "originLocationCode") String originLocationCode,
                              @RequestParam(name = "destinationLocationCode") String destinationLocationCode,
                              @RequestParam(name = "departureDate") String departureDate,
                              @RequestParam(name = "adults") int adults, @RequestParam(name = "max") int max);

    @GetMapping("v2/shopping/flight-offers")
    String flightOffersSearch(@RequestHeader(name = "Authorization") String authorization,
                              @RequestParam(name = "originLocationCode") String originLocationCode,
                              @RequestParam(name = "destinationLocationCode") String destinationLocationCode,
                              @RequestParam(name = "departureDate") String departureDate,
                              @RequestParam(name = "returnDate") String returnDate,
                              @RequestParam(name = "adults") int adults, @RequestParam(name = "max") int max);

    @PostMapping(value = "v1/shopping/flight-offers/pricing", consumes = "application/json")
    String flightOffersPrice(@RequestHeader(name = "Authorization") String authorization,
                             String flightOffer);

    @PostMapping(value = "v1/booking/flight-orders", consumes = "application/json")
    String flightCreateOrders(@RequestHeader(name = "Authorization") String authorization,
                              String body);

    @GetMapping("v1/booking/flight-orders/{id}")
    String flightOrderManagement(@RequestHeader(name = "Authorization") String authorization,
                                 @PathVariable(name = "id") String id);

    @DeleteMapping("v1/booking/flight-orders/{id}")
    void flightDeleteOrders(@RequestHeader(name = "Authorization") String authorization,
                            @PathVariable(name = "id") String id);
}
