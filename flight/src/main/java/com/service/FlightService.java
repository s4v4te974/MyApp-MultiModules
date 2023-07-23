package com.service;

import com.dto.SearchCriteria;
import com.entity.Cities;
import com.entity.Plane;
import com.exception.FlightException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    List<Cities> availableCities();

    double calculateDistance(int departure, int arrival) throws FlightException;

    double calculatePrice(SearchCriteria criteria) throws FlightException;

    List<Plane> retrieveplanes() throws FlightException;
}
