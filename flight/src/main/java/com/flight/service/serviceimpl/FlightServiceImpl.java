package com.flight.service.serviceimpl;

import com.flight.dto.SearchCriteria;
import com.flight.entity.Cities;
import com.flight.entity.Plane;
import com.flight.exception.FlightException;
import com.flight.repository.CitiesRepository;
import com.flight.repository.PlaneRepository;
import com.flight.service.FlightService;
import com.flight.utils.FlightConsts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

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
        log.info("Start calculate distance");
        Optional<Cities> departure = citiesRepository.findById(idDeparture);
        Optional<Cities> arrival = citiesRepository.findById(idArrival);

        if (departure.isEmpty() || arrival.isEmpty()) {
            throw new FlightException(FlightConsts.UNABLE_TO_RETRIEVE_CITIES);
        } else {
            double lat1 = Math.toRadians(departure.get().getLattitude());
            double lon1 = Math.toRadians(departure.get().getLongitude());
            double lat2 = Math.toRadians(arrival.get().getLattitude());
            double lon2 = Math.toRadians(arrival.get().getLongitude());
            double earthRadius = 6371.01;
            double rawDistance = earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
            return formatResult(rawDistance);
        }
    }

    @Override
    public double calculatePrice(SearchCriteria criteria) throws FlightException {
        log.info("Start calculate price");
        Optional<Cities> departure = citiesRepository.findById(criteria.getIdDeparture());
        Optional<Cities> arrival = citiesRepository.findById(criteria.getIdArrival());

        if (departure.isEmpty() || arrival.isEmpty()) {
            throw new FlightException(FlightConsts.UNABLE_TO_CALCULATE_PRICE);
        } else {
            Double tax = taxByClass(criteria.getPassengerClass().name(), departure.get(), arrival.get());
            return formatResult(tax);
        }
    }

    @Override
    public List<Plane> retrieveplanes() throws FlightException {
        log.info("Retrieve all the planes");
        try {
            return planeRepository.findAll();
        } catch (DataAccessException e) {
            throw new FlightException(FlightConsts.UNABLE_TO_FIND_PLANE);
        }
    }

    private double taxByClass(String passengerClass, Cities departure, Cities arrival) {
        log.info("Determine tax by passengers class");
        if (departure.isUe() && arrival.isUe()) {
            return switch (passengerClass) {
                case "FIRSTCLASS" -> FlightConsts.PRESTATION_FIRST_UE + FlightConsts.SOLIDARITY_TAX_UE_MAX + FlightConsts.REDEVANCE_PASSENGER_MAX
                        + FlightConsts.TAX_AV_CIVILE_FR + FlightConsts.SECURITY_TAX_MAX;
                case "AFFAIR" -> FlightConsts.PRESTATION_AFFAIR_UE + FlightConsts.SOLIDARITY_TAX_UE_MAX + FlightConsts.REDEVANCE_PASSENGER_MAX
                        + FlightConsts.TAX_AV_CIVILE_FR + FlightConsts.SECURITY_TAX_MAX;
                default -> FlightConsts.PRESTATION_ECONOMIC_UE + FlightConsts.SOLIDARITY_TAX_UE + FlightConsts.REDEVANCE_PASSENGER
                        + FlightConsts.TAX_AV_CIVILE_FR + FlightConsts.SECURITY_TAX;
            };
        } else {
            return switch (passengerClass) {
                case "FIRSTCLASS" -> FlightConsts.PRESTATION_FIRST + FlightConsts.SOLIDARITY_TAX_NON_UE_MAX + FlightConsts.REDEVANCE_PASSENGER_MAX
                        + FlightConsts.TAX_AV_CIVILE_OTHER + FlightConsts.SECURITY_TAX_MAX;
                case "AFFAIR" -> FlightConsts.PRESTATION_AFFAIR + FlightConsts.SOLIDARITY_TAX_NON_UE_MAX + FlightConsts.REDEVANCE_PASSENGER_MAX
                        + FlightConsts.TAX_AV_CIVILE_OTHER + FlightConsts.SECURITY_TAX_MAX;
                default -> FlightConsts.PRESTATION_ECONOMICS + FlightConsts.SOLIDARITY_TAX_NON_UE + FlightConsts.REDEVANCE_PASSENGER
                        + FlightConsts.TAX_AV_CIVILE_OTHER + FlightConsts.SECURITY_TAX;
            };
        }
    }

    private double formatResult(Double input) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(input).replace(",", "."));
    }
}