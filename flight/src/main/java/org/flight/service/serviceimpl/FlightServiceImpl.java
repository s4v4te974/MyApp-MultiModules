package org.flight.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flight.dto.SearchCriteria;
import org.flight.entity.Cities;
import org.flight.entity.Plane;
import org.flight.exception.FlightException;
import org.flight.repository.CitiesRepository;
import org.flight.repository.PlaneRepository;
import org.flight.service.FlightService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import static org.flight.utils.FlightConsts.PRESTATION_AFFAIR;
import static org.flight.utils.FlightConsts.PRESTATION_AFFAIR_UE;
import static org.flight.utils.FlightConsts.PRESTATION_ECONOMICS;
import static org.flight.utils.FlightConsts.PRESTATION_ECONOMIC_UE;
import static org.flight.utils.FlightConsts.PRESTATION_FIRST;
import static org.flight.utils.FlightConsts.PRESTATION_FIRST_UE;
import static org.flight.utils.FlightConsts.REDEVANCE_PASSENGER;
import static org.flight.utils.FlightConsts.REDEVANCE_PASSENGER_MAX;
import static org.flight.utils.FlightConsts.SECURITY_TAX;
import static org.flight.utils.FlightConsts.SECURITY_TAX_MAX;
import static org.flight.utils.FlightConsts.SOLIDARITY_TAX_NON_UE;
import static org.flight.utils.FlightConsts.SOLIDARITY_TAX_NON_UE_MAX;
import static org.flight.utils.FlightConsts.SOLIDARITY_TAX_UE;
import static org.flight.utils.FlightConsts.SOLIDARITY_TAX_UE_MAX;
import static org.flight.utils.FlightConsts.TAX_AV_CIVILE_FR;
import static org.flight.utils.FlightConsts.TAX_AV_CIVILE_OTHER;
import static org.flight.utils.FlightConsts.UNABLE_TO_CALCULATE_PRICE;
import static org.flight.utils.FlightConsts.UNABLE_TO_FIND_PLANE;
import static org.flight.utils.FlightConsts.UNABLE_TO_RETRIEVE_CITIES;


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

        Optional<Cities> departure = citiesRepository.findById(criteria.getIdDeparture());
        Optional<Cities> arrival = citiesRepository.findById(criteria.getIdArrival());

        if (departure.isEmpty() || arrival.isEmpty()) {
            throw new FlightException(UNABLE_TO_CALCULATE_PRICE);
        } else {
            Double tax = taxByClass(criteria.getPassengerClass().name(), departure.get(), arrival.get());
            return formatResult(tax);
        }
    }

    @Override
    public List<Plane> retrieveplanes() throws FlightException {
        try {
            return planeRepository.findAll();
        } catch (DataAccessException e) {
            throw new FlightException(UNABLE_TO_FIND_PLANE);
        }
    }

    private double taxByClass(String passengerClass, Cities departure, Cities arrival) {

        if (departure.isUe() && arrival.isUe()) {
            return switch (passengerClass) {
                case "FIRSTCLASS" -> PRESTATION_FIRST_UE + SOLIDARITY_TAX_UE_MAX + REDEVANCE_PASSENGER_MAX
                        + TAX_AV_CIVILE_FR + SECURITY_TAX_MAX;
                case "AFFAIR" -> PRESTATION_AFFAIR_UE + SOLIDARITY_TAX_UE_MAX + REDEVANCE_PASSENGER_MAX
                        + TAX_AV_CIVILE_FR + SECURITY_TAX_MAX;
                default -> PRESTATION_ECONOMIC_UE + SOLIDARITY_TAX_UE + REDEVANCE_PASSENGER
                        + TAX_AV_CIVILE_FR + SECURITY_TAX;
            };
        } else {
            return switch (passengerClass) {
                case "FIRSTCLASS" -> PRESTATION_FIRST + SOLIDARITY_TAX_NON_UE_MAX + REDEVANCE_PASSENGER_MAX
                        + TAX_AV_CIVILE_OTHER + SECURITY_TAX_MAX;
                case "AFFAIR" -> PRESTATION_AFFAIR + SOLIDARITY_TAX_NON_UE_MAX + REDEVANCE_PASSENGER_MAX
                        + TAX_AV_CIVILE_OTHER + SECURITY_TAX_MAX;
                default -> PRESTATION_ECONOMICS + SOLIDARITY_TAX_NON_UE + REDEVANCE_PASSENGER
                        + TAX_AV_CIVILE_OTHER + SECURITY_TAX;
            };
        }
    }

    private double formatResult(Double input) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(input).replace(",", "."));
    }
}