package com.flight.service;

import com.flight.dto.SearchCriteria;
import com.flight.entity.Cities;
import com.flight.entity.Plane;
import com.flight.exception.FlightException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    List<Cities> availableCities();

    double calculateDistance(int departure, int arrival) throws FlightException;

    double calculatePrice(SearchCriteria criteria) throws FlightException;

    List<Plane> retrieveplanes() throws FlightException;
}
