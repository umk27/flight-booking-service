package com.userinterface.controller;

import com.userinterface.feignclient.BackendClient;
import com.userinterface.model.CreateOrderData;
import com.userinterface.model.FlightOfferData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FrontendController {

    private CreateOrderData createOrderData = new CreateOrderData();

    private List<CreateOrderData.Traveler> travelers = new ArrayList<>();

    private List<FlightOfferData> flightOfferDataList;

    private FlightOfferData flightOfferDataPrice;

    private int adults;

    private int i = 1;

    public List<FlightOfferData> getFlightOfferData() {
        return flightOfferDataList;
    }

    public void setFlightOfferData(List<FlightOfferData> flightOfferDataList) {
        this.flightOfferDataList = flightOfferDataList;
    }

    public BackendClient getBackendClient() {
        return backendClient;
    }

    private final BackendClient backendClient;

    public FrontendController(BackendClient backendClient) {
        this.backendClient = backendClient;
    }

    @GetMapping("find-tickets")
    public String findTickets() {
        return "find-tickets";

    }

    @GetMapping("tickets-info")
    public String ticketsInfo(@RequestParam("originLocationCode") String originLocationCode,
                              @RequestParam("destinationLocationCode") String destinationLocationCode,
                              @RequestParam("departureDate") String departureDate,
                              @RequestParam("returnDate") String returnDate,
                              @RequestParam("adults") String adults1,
                              @RequestParam("max") String max,
                              Model model) {

        adults = Integer.parseInt(adults1);

        flightOfferDataList = backendClient.flightOfferSearch(originLocationCode, destinationLocationCode,
                departureDate, returnDate, adults, Integer.parseInt(max));

        model.addAttribute("flightOffers", flightOfferDataList);

        return "tickets-info-1";
    }

    @GetMapping("tickets-price")
    public String ticketsPrice(@RequestParam("id") String id, Model model) {

        /*Object FO = model.getAttribute("flightOffers");

        List<FlightOfferData> flightOffers = (List<FlightOfferData>) FO;
        System.out.println(flightOffers.get(0).getPrice().getTotal());*/

        FlightOfferData flightOfferData = flightOfferDataList.get(Integer.parseInt(id));

        flightOfferDataPrice = backendClient.flightOfferPrice(flightOfferData);
        model.addAttribute("flightOfferDataPrice", flightOfferDataPrice);

        return "tickets-price-1";
    }

    @GetMapping("add-traveler")
    public String addTravelers(Model model) {
        model.addAttribute("i", i);

        return "add-traveler";
    }

    @PostMapping("book-tickets")
    public String bookTickets(@RequestParam("dateOfBirth") String dateOfBirth,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("gender") String gender,
                              @RequestParam("emailAddress") String emailAddress,
                              @RequestParam("phone") String phone,
                              @RequestParam("birthPlace") String birthPlace,
                              @RequestParam("issuanceLocation") String issuanceLocation,
                              @RequestParam("issuanceDate") String issuanceDate,
                              @RequestParam("number") String number,
                              @RequestParam("expiryDate") String expiryDate,
                              @RequestParam("issuanceCountry") String issuanceCountry,
                              Model model) {
        CreateOrderData.Traveler traveler = new CreateOrderData.Traveler();
        traveler.setId(String.valueOf(i));
        traveler.setDateOfBirth(dateOfBirth);
        traveler.setFirstName(firstName);
        traveler.setLastName(lastName);
        traveler.setGender(gender);
        traveler.setEmailAddress(emailAddress);
        traveler.setPhone(phone);
        CreateOrderData.Traveler.Document document = new CreateOrderData.Traveler.Document();
        document.setDocumentType("PASSPORT");
        document.setBirthPlace(birthPlace);
        document.setIssuanceLocation(issuanceLocation);
        document.setIssuanceDate(issuanceDate);
        document.setNumber(number);
        document.setExpiryDate(expiryDate);
        document.setIssuanceCountry(issuanceCountry);
        document.setValidityCountry(issuanceCountry);
        document.setNationality(issuanceCountry);
        document.setHolder(true);
        traveler.setDocument(document);
        System.out.println(traveler);
        travelers.add(traveler);
        i = i + 1;
        if (i <= adults) {
            return "add-traveler";
        }

        createOrderData.setTravelers(travelers);
        createOrderData.setFlightOfferData(flightOfferDataPrice);

        String id = backendClient.flightCreateOrder(createOrderData);
        model.addAttribute("id", id);

        return "book-tickets";
    }

    @GetMapping("delete-order")
    public String deleteOrder(){
        return "delete-order";
    }

    @GetMapping("order-delete")
    public String orderDelete(@RequestParam("id") String id) {

        backendClient.flightDeleteOrder(id);

        return "order-delete";
    }

    @GetMapping("info-order")
    public String orderInfo(){
        return "info-order";
    }

    @GetMapping("order-info")
    public String orderInfo(@RequestParam("id") String id,
                            Model model) {

        CreateOrderData orderData = backendClient.flightOrderManagement(id);
        model.addAttribute("orderData", orderData);

        return "order-info";
    }

}
