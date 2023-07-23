package org.flight.service;

import org.flight.dto.SearchCriteria;
import org.flight.entity.Cities;
import org.flight.entity.Plane;
import org.flight.exception.FlightException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    List<Cities> availableCities();

    double calculateDistance(int departure, int arrival) throws FlightException;

    double calculatePrice(SearchCriteria criteria) throws FlightException;

    List<Plane> retrieveplanes() throws FlightException;
}
