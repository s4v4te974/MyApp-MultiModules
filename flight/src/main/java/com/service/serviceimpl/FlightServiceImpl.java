package com.service.serviceimpl;

import com.dto.SearchCriteria;
import com.entity.Cities;
import com.entity.Plane;
import com.exception.FlightException;
import com.repository.CitiesRepository;
import com.repository.PlaneRepository;
import com.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import static com.utils.FlightConsts.PRESTATION_AFFAIR;
import static com.utils.FlightConsts.PRESTATION_AFFAIR_UE;
import static com.utils.FlightConsts.PRESTATION_ECONOMICS;
import static com.utils.FlightConsts.PRESTATION_ECONOMIC_UE;
import static com.utils.FlightConsts.PRESTATION_FIRST;
import static com.utils.FlightConsts.PRESTATION_FIRST_UE;
import static com.utils.FlightConsts.REDEVANCE_PASSENGER;
import static com.utils.FlightConsts.REDEVANCE_PASSENGER_MAX;
import static com.utils.FlightConsts.SECURITY_TAX;
import static com.utils.FlightConsts.SECURITY_TAX_MAX;
import static com.utils.FlightConsts.SOLIDARITY_TAX_NON_UE;
import static com.utils.FlightConsts.SOLIDARITY_TAX_NON_UE_MAX;
import static com.utils.FlightConsts.SOLIDARITY_TAX_UE;
import static com.utils.FlightConsts.SOLIDARITY_TAX_UE_MAX;
import static com.utils.FlightConsts.TAX_AV_CIVILE_FR;
import static com.utils.FlightConsts.TAX_AV_CIVILE_OTHER;
import static com.utils.FlightConsts.UNABLE_TO_CALCULATE_PRICE;
import static com.utils.FlightConsts.UNABLE_TO_FIND_PLANE;
import static com.utils.FlightConsts.UNABLE_TO_RETRIEVE_CITIES;

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
        log.info("Start calculate price");
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
        log.info("Retrieve all the planes");
        try {
            return planeRepository.findAll();
        } catch (DataAccessException e) {
            throw new FlightException(UNABLE_TO_FIND_PLANE);
        }
    }

    private double taxByClass(String passengerClass, Cities departure, Cities arrival) {
        log.info("Determine tax by passengers class");
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