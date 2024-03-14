package com.userinterface.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "AMADEUS-CLIENT")
public interface BackendClient {

    @GetMapping("backend/getIATACode")
    String getIATACode(@RequestParam(name = "originLocation") String originLocation,
                       @RequestParam(name = "destinationLocation") String destinationLocation);
}
