package service;


import dto.SearchCriteria;
import entity.Cities;
import entity.Plane;
import exception.FlightException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    List<Cities> availableCities();

    double calculateDistance(int departure, int arrival) throws FlightException;

    double calculatePrice(SearchCriteria criteria) throws FlightException;

    List<Plane> retrieveplanes() throws FlightException;
}
