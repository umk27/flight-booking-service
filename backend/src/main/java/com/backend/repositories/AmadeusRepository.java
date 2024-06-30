package com.backend.repositories;

import com.backend.amadeus.AmadeusClient;
import com.backend.factory.FlightCreateOrderJsonFactory;
import com.backend.factory.FlightOfferPriceJsonFactory;
import com.backend.model.CreateOrderData;
import com.backend.model.FlightOfferData;
import com.backend.parsers.FlightCreateOrderParser;
import com.backend.parsers.FlightOfferPriceParser;
import com.backend.parsers.FlightOfferSearchParser;
import com.backend.parsers.FlightOrderManagementParser;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmadeusRepository {

    private final AmadeusClient amadeusClient;

    private final FlightOfferSearchParser flightOfferSearchParser;

    private final FlightOfferPriceParser flightOfferPriceParser;

    private final FlightCreateOrderParser flightCreateOrderParser;

    private final FlightOrderManagementParser flightOrderManagementParser;

    private final FlightOfferPriceJsonFactory flightOfferPriceJsonFactory;

    private final FlightCreateOrderJsonFactory flightCreateOrderJsonFactory;

    private final static Logger logger = LoggerFactory.getLogger(AmadeusRepository.class);

    public AmadeusRepository(AmadeusClient amadeusClient, FlightOfferSearchParser parser, FlightOfferPriceParser flightOfferPriceParser, FlightCreateOrderParser flightCreateOrderParser, FlightOrderManagementParser flightOrderManagementParser, FlightOfferPriceJsonFactory flightOfferPriceJsonFactory, FlightCreateOrderJsonFactory flightCreateOrderJsonFactory) {
        this.amadeusClient = amadeusClient;
        this.flightOfferSearchParser = parser;
        this.flightOfferPriceParser = flightOfferPriceParser;
        this.flightCreateOrderParser = flightCreateOrderParser;
        this.flightOrderManagementParser = flightOrderManagementParser;
        this.flightOfferPriceJsonFactory = flightOfferPriceJsonFactory;
        this.flightCreateOrderJsonFactory = flightCreateOrderJsonFactory;
    }


    public List<FlightOfferData> flightOffersSearch(String authorization, String originLocationCode, String destinationLocationCode,
                                                    String departureDate, int adults, int max) {

        logger.info("Поиск предложений полетов на Amadeus");
        String json = amadeusClient.flightOffersSearch(authorization, originLocationCode, destinationLocationCode,
                departureDate, adults, max);
        logger.info("Предложения полетов получены");
        logger.info("Парсинг JSON предложений полетов");
        List<FlightOfferData> flightOfferDataList = flightOfferSearchParser.parse(json);
        logger.info("Парсинг JSON предложений полетов прошел успешно");
        return flightOfferDataList;
    }

    public List<FlightOfferData> flightOffersSearch(String authorization, String originLocationCode, String destinationLocationCode,
                                                    String returnDate, String departureDate, int adults, int max) {
        logger.info("Поиск предложений полетов на Amadeus");
        String json = amadeusClient.flightOffersSearch(authorization, originLocationCode, destinationLocationCode,
                departureDate, returnDate, adults, max);
        logger.info("Предложения полетов получены");
        logger.info("Парсинг JSON предложений полетов");
        List<FlightOfferData> flightOfferDataList = flightOfferSearchParser.parse(json);
        logger.info("Парсинг JSON предложений полетов прошел успешно");
        return flightOfferDataList;
    }

    public FlightOfferData flightOfferPrice(String token, FlightOfferData flightOfferData) {
        logger.info("Уточнение цены полетов на Amadeus");
        logger.info("Создание JSON запроса");
        JsonNode flightOfferRequest = flightOfferPriceJsonFactory.build(flightOfferData.getFlightOffer());
        String str = flightOfferRequest.toString();
        logger.info("JSON запроса создан");
        String json = amadeusClient.flightOffersPrice(token, str);
        logger.info("Уточнение цены полета прошло успешно");
        logger.info("Парсинг JSON предложений полетов");
        FlightOfferData flightOfferPriceData = flightOfferPriceParser.parse(json);
        if (flightOfferPriceData.getPrice() != null) {
            FlightOfferData.Price price = flightOfferData.getPrice();
            price.setTotal(flightOfferPriceData.getPrice().getTotal());
            flightOfferData.setPrice(price);
        }
        logger.info("Парсинг JSON предложений полетов прошел успешно");
        return flightOfferData;
    }

    public String flightCreateOrder(String token, CreateOrderData createOrderData) {
        logger.info("Бронирование билетов");
        logger.info("Создание JSON запроса");
        JsonNode createOrderRequest = flightCreateOrderJsonFactory.build(createOrderData);
        String str = createOrderRequest.toString();
        logger.info("JSON запроса создан");
        String json = amadeusClient.flightCreateOrders(token, str);
        logger.info("Бронирование произведено успешно");
        logger.info("Парсинг JSON результатов бронирования");
        String str1 = flightCreateOrderParser.parse(json);
        logger.info("Парсинг JSON прошел успешно");
        return str1;

    }

    public CreateOrderData flightOrderManagement(String token, String id) {
        logger.info("Получение информации о бронировании");
        String json = amadeusClient.flightOrderManagement(token, id);
        logger.info("Информация о бронировании полоучена");
        logger.info("Парсинг JSON информации о бронировании");
        CreateOrderData createOrderData = flightOrderManagementParser.parse(json);
        logger.info("Парсинг JSON информации о бронировании прошел успешно");
        return createOrderData;
    }

    public void flightDeleteOrder(String token, String id) {
        logger.info("Удаление бронирования");
        amadeusClient.flightDeleteOrders(token, id);
        logger.info("Удаление бронирования произведено");
    }
}
