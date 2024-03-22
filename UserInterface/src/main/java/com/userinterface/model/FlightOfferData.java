package com.userinterface.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlightOfferData {

    private String id;
    private JsonNode flightOffer;
    private List<Itinerarie> itineraries;
    private Price price;


    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Itinerarie{

        private List<Segment> segments;

        @NoArgsConstructor
        @Getter
        @Setter
        @ToString
        public static class Segment {

            private Arrival arrival;
            private Departure departure;

            @NoArgsConstructor
            @Getter
            @Setter
            @ToString
            public static class Departure {
                String iataCode;
                String terminal;
                String at;

            }

            @NoArgsConstructor
            @Getter
            @Setter
            @ToString
            public static class Arrival {
                String iataCode;
                String terminal;
                String at;

            }
        }

    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Price {
        String currency;
        String total;
    }
}
