package com.ws.flight;

import com.flight.businesslogic.FlightBusinessLogic;
import com.flight.dto.ProposedFlight;
import com.flight.dto.SearchCriteria;
import com.flight.entity.Cities;
import com.flight.exception.FlightException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ws.utils.WebServiceConsts.CITIES;
import static com.ws.utils.WebServiceConsts.DEFAULT_PATH;
import static com.ws.utils.WebServiceConsts.FLIGHTS;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = DEFAULT_PATH)
public class FlightController {

    private final FlightBusinessLogic flightBusinessLogic;

    @GetMapping(value = CITIES,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Cities>> retrieveCities() {
        return new ResponseEntity<>(flightBusinessLogic.availableCities(), HttpStatus.OK);
    }

    @GetMapping(value = FLIGHTS,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProposedFlight>> retrieveFlights(@RequestBody SearchCriteria criteria) throws FlightException {
        return new ResponseEntity<>(flightBusinessLogic.availableFlights(criteria), HttpStatus.OK);
    }
}
