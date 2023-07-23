package com.service;


import com.dto.SearchCriteria;
import com.entity.Cities;
import com.entity.Plane;
import com.enums.PassengerClass;
import com.exception.FlightException;
import com.repository.CitiesRepository;
import com.repository.PlaneRepository;
import com.service.serviceimpl.FlightServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.utils.FlightConsts.UNABLE_TO_CALCULATE_PRICE;
import static com.utils.FlightConsts.UNABLE_TO_FIND_PLANE;
import static com.utils.FlightConsts.UNABLE_TO_RETRIEVE_CITIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    private static Cities city1;
    private static Cities city2;
    @InjectMocks
    FlightServiceImpl flightService;
    @Mock
    PlaneRepository planeRepository;
    @Mock
    CitiesRepository citiesRepository;

    @BeforeAll
    static void setUp() {
        city1 = Cities.builder()
                .id(1L) //
                .isUe(true) //
                .name("Paris") //
                .country("France") //
                .lattitude(43.60) //
                .longitude(1.44) //
                .build(); //

        city2 = Cities.builder()
                .id(2L) //
                .isUe(true) //
                .name("Toulouse") //
                .country("France") //
                .lattitude(48.85) //
                .longitude(2.35) //
                .build(); //
    }

    @Test
    void retrievePlanesTest() throws FlightException {
        Plane plane1 = Plane.builder()
                .id(1) //
                .speed(950d) //
                .builder("AIRBUS") //
                .model("A330-200") //
                .range(11750.00) //
                .conso(2.4) //
                .build(); //

        Plane plane2 = Plane.builder()
                .id(1) //
                .speed(950d) //
                .builder("AIRBUS") //
                .model("A330-200") //
                .range(11750.00) //
                .conso(2.4) //
                .build(); //

        List<Plane> expectedPlane = new ArrayList<>(Arrays.asList(plane1, plane2));
        when(planeRepository.findAll()).thenReturn(expectedPlane);
        List<Plane> retrieved = flightService.retrieveplanes();
        assertIterableEquals(expectedPlane, retrieved);
    }

    @Test
    void calculateDistanceTest() throws FlightException {

        when(citiesRepository.findById(1)).thenReturn(Optional.ofNullable(city1));
        when(citiesRepository.findById(2)).thenReturn(Optional.ofNullable(city2));

        double distance = flightService.calculateDistance(1, 2);

        assertEquals(587.94, distance);
    }

    @Test
    void calculatePrice() throws FlightException {

        SearchCriteria criteria = SearchCriteria.builder()
                .idArrival(2) //
                .idDeparture(1) //
                .passengerClass(PassengerClass.ECONOMIC) //
                .build();

        when(citiesRepository.findById(criteria.getIdDeparture())).thenReturn(Optional.ofNullable(city1));
        when(citiesRepository.findById(criteria.getIdArrival())).thenReturn(Optional.ofNullable(city2));

        // part UE
        // economics
        double taxEconomicsUe = flightService.calculatePrice(criteria);
        assertEquals(36.13, taxEconomicsUe);

        // affair
        criteria.setPassengerClass(PassengerClass.AFFAIR);
        double taxAffairUe = flightService.calculatePrice(criteria);
        assertEquals(138.17, taxAffairUe);

        // first class
        criteria.setPassengerClass(PassengerClass.FIRSTCLASS);
        double taxFirstUe = flightService.calculatePrice(criteria);
        assertEquals(838.17, taxFirstUe);

        // Part non UE
        city1.setUe(false);
        city2.setUe(false);
        // affair
        // first class
        double taxFirst = flightService.calculatePrice(criteria);
        assertEquals(2075.47, taxFirst);

        // affair
        criteria.setPassengerClass(PassengerClass.AFFAIR);
        double taxEconomics = flightService.calculatePrice(criteria);
        assertEquals(275.47, taxEconomics);

        // economics
        criteria.setPassengerClass(PassengerClass.ECONOMIC);
        double taxAffair = flightService.calculatePrice(criteria);
        assertEquals(68.0, taxAffair);
    }

    @Test
    void retrievePlaneException() {
        doThrow(new DataAccessException("Error") {
        }).when(planeRepository).findAll();
        Exception exception = assertThrows(FlightException.class,
                () -> flightService.retrieveplanes());
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(UNABLE_TO_FIND_PLANE));
    }

    @Test
    void calculateDistanceException() {
        when(citiesRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(FlightException.class,
                () -> flightService.calculateDistance(1, 2));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(UNABLE_TO_RETRIEVE_CITIES));
    }

    @Test
    void calculatePriceException() {
        SearchCriteria criteria = SearchCriteria.builder() //
                .idDeparture(1) //
                .idArrival(2) //
                .build(); //
        when(citiesRepository.findById(anyInt())).thenReturn(Optional.empty());
        Exception exception = assertThrows(FlightException.class,
                () -> flightService.calculatePrice(criteria));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(UNABLE_TO_CALCULATE_PRICE));
    }
}