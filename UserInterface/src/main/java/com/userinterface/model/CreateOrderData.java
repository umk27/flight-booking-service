package com.userinterface.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateOrderData {

    private List<Traveler> travelers;

    private FlightOfferData flightOfferData;

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Traveler {

        private String id;

        private String dateOfBirth;

        private String firstName;

        private String lastName;

        private String gender;

        private String emailAddress;

        private String phone;

        private Document document;

        @NoArgsConstructor
        @Getter
        @Setter
        @ToString
        public static class Document {

            private String documentType;

            private String birthPlace;

            private String issuanceLocation;

            private String issuanceDate;

            private String number;

            private String expiryDate;

            private String issuanceCountry;

            private String validityCountry;

            private String nationality;

            private boolean holder;
        }
    }
}
