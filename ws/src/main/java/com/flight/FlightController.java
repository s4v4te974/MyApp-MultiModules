package com.flight;

import com.businesslogic.FlightBusinessLogic;
import com.dto.ProposedFlight;
import com.dto.SearchCriteria;
import com.entity.Cities;
import com.exception.FlightException;
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

import static com.utils.WebServiceConsts.CITIES;
import static com.utils.WebServiceConsts.DEFAULT_PATH;
import static com.utils.WebServiceConsts.FLIGHTS;

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
