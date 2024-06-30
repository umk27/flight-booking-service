package com.backend.travelpayouts;

import com.backend.amadeus.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "travelPayoutsClient", url = "https://www.travelpayouts.com", configuration = FeignConfig.class)
public interface TravelPayoutsClient {

    @GetMapping("widgets_suggest_params?q=Из%20{originLocation}%20в%20{destinationLocation}")
    public String getIATACode(@PathVariable(name = "originLocation") String originLocation,
                              @PathVariable(name = "destinationLocation") String destinationLocation);
}
