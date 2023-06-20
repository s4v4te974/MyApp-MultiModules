package org.flight.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flight.dto.SearchCriteria;
import org.flight.entity.Cities;
import org.flight.entity.Plane;
import org.flight.enums.PassengerClass;
import org.flight.exception.FlightException;
import org.flight.repository.CitiesRepository;
import org.flight.repository.PlaneRepository;
import org.flight.service.FlightService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.flight.utils.FlightConsts.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final PlaneRepository planeRepository;

    private final CitiesRepository citiesRepository;

    @Override
    public List<Cities> availableCities() {
        return citiesRepository.findAll();
    }

    @Override
    public double calculateDistance(int idDeparture, int idArrival) throws FlightException {
        Optional<Cities> departure = citiesRepository.findById(idDeparture);
        Optional<Cities> arrival = citiesRepository.findById(idArrival);

        if (departure.isEmpty() || arrival.isEmpty()) {
            throw new FlightException(UNABLE_TO_RETRIEVE_CITIES);
        } else {
            double lat1 = Math.toRadians(departure.get().getXCoordonates());
            double lon1 = Math.toRadians(departure.get().getYCoordonates());
            double lat2 = Math.toRadians(arrival.get().getXCoordonates());
            double lon2 = Math.toRadians(arrival.get().getYCoordonates());
            double earthRadius = 6371.01;
            return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
        }
    }

    @Override
    public double calculatePrice(SearchCriteria criteria) throws FlightException {

        Optional<Cities> departure = citiesRepository.findById(criteria.getIdDeparture());
        Optional<Cities> arrival = citiesRepository.findById(criteria.getIdArrival());

        if (departure.isEmpty() || arrival.isEmpty()) {
            throw new FlightException(UNABLE_TO_RETRIEVE_CITIES);
        } else {
            return taxByClass(criteria.getPassengerClass().name(), departure.get(), arrival.get());
        }
    }

    @Override
    public List<Plane> retrieveplanes() throws FlightException {
        try {
            return planeRepository.findAll();
        }catch(DataAccessException e){
            throw new FlightException(UNABLE_TO_FIND_PLANE);
        }
    }

    private double taxByClass(String passengerClass, Cities departure, Cities arrival){
        double tax = 0;
        if(departure.isUe() && arrival.isUe()){
            if(passengerClass.equals(PassengerClass.FIRSTCLASS.name())){
                 tax += PRESTATION_FIRST_UE + SOLIDARITY_TAX_UE_MAX + REDEVANCE_PASSENGER_MAX
                        + TAX_AV_CIVILE_FR + SECURITY_TAX;
            }else if (passengerClass.equals(PassengerClass.AFFAIR.name())){
                tax += PRESTATION_AFFAIR_UE + SOLIDARITY_TAX_UE_MAX + REDEVANCE_PASSENGER_MAX
                        + TAX_AV_CIVILE_FR + SECURITY_TAX;
            }else{
                tax += PRESTATION_ECONOMIC_UE + SOLIDARITY_TAX_UE + REDEVANCE_PASSENGER
                        + TAX_AV_CIVILE_FR + SECURITY_TAX;
            }
        }else{
            if(passengerClass.equals(PassengerClass.FIRSTCLASS.name())){
                tax += PRESTATION_FIRST + SOLIDARITY_TAX_NON_UE_MAX + REDEVANCE_PASSENGER_MAX
                        + TAX_AV_CIVILE_OTHER + SECURITY_TAX;
            }else if (passengerClass.equals(PassengerClass.AFFAIR.name())){
                tax += PRESTATION_AFFAIR + SOLIDARITY_TAX_NON_UE_MAX + REDEVANCE_PASSENGER_MAX
                        + TAX_AV_CIVILE_OTHER + SECURITY_TAX;
            }else{
                tax += PRESTATION_ECONOMICS + SOLIDARITY_TAX_NON_UE + REDEVANCE_PASSENGER
                        + TAX_AV_CIVILE_OTHER + SECURITY_TAX;
            }
        }
    return tax;
    }
}