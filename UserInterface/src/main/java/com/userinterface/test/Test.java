package com.userinterface.test;

import com.userinterface.feignclient.BackendClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    private final BackendClient backendClient;

    public Test(BackendClient backendClient) {
        this.backendClient = backendClient;
    }


    @GetMapping("test")
    public String test(@RequestParam(name = "originLocation") String originLocation,
                       @RequestParam(name = "destinationLocation") String destinationLocation) {
        return backendClient.getIATACode(originLocation, destinationLocation);
    }
}
