package org.flight.businesslogic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flight.dto.ProposedFlight;
import org.flight.dto.SearchCriteria;
import org.flight.entity.Cities;
import org.flight.entity.Plane;
import org.flight.exception.FlightException;
import org.flight.service.FlightService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightBusinessLogic {

    private final FlightService flightService;

    public List<Cities> availableCities() {
        return flightService.availableCities();
    }

    public List<ProposedFlight> availableFlights(SearchCriteria criteria) throws FlightException {
        double distance = flightService.calculateDistance(criteria.getIdDeparture(), criteria.getIdArrival());
        List<Plane> planes = flightService.retrieveplanes();
        Plane plane = planes.stream().filter(p -> p.getRange() > distance
                && p.isAvailable()).findFirst().orElse(null);
        if(plane == null){
            return Collections.emptyList();
        }
        double price = calculatePrice(criteria);
        double finalPrice = calculateFinalPrice(plane.getConso(), distance, price);

        ProposedFlight proposedFlight = ProposedFlight.builder() //
                .price(finalPrice) //
                .passengerClass(criteria.getPassengerClass()) //
                .plane(plane) //
                .build();

        return new ArrayList<>(Collections.singletonList(proposedFlight));
    }

    private double calculatePrice(SearchCriteria criteria) throws FlightException {
        double priceByClass = 0;
        try {
           priceByClass = flightService.calculatePrice(criteria);
        } catch (FlightException e) {
            throw new FlightException(e.getMessage());
        }
        double priceMonth = priceByMonth(criteria.getDate(), priceByClass);
        return priceByDay(criteria.getDate(), priceMonth);
    }

    private double priceByDay(LocalDateTime date, double tax){
        DayOfWeek day = date.getDayOfWeek();
        double price;
        switch (day) {
            case FRIDAY, SATURDAY -> price = 0.16;
            case TUESDAY -> price = 0.05;
            case SUNDAY -> price = 0.1;
            default -> price = 0;
        }
        double purcentage = tax * price;
        return tax + purcentage;
    }

    private double priceByMonth(LocalDateTime date, double tax){
        Month month = date.getMonth();
        double price;
        switch (month) {
            case JULY, AUGUST, DECEMBER, JANUARY -> price = 0.18;
            case OCTOBER, APRIL -> price = 0.1;
            default -> price = 0;
        }
        double purcentage = tax * price;
        return tax + purcentage;
    }

    private double calculateFinalPrice(double conso, double distance, double price){
        double totalPrice = (distance / 100) * conso;
        return (totalPrice * 1.18) + price;
    }
}