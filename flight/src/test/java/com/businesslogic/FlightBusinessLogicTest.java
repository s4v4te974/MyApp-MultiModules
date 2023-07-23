package com.businesslogic;

import com.dto.ProposedFlight;
import com.dto.SearchCriteria;
import com.entity.Plane;
import com.enums.PassengerClass;
import com.exception.FlightException;
import com.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightBusinessLogicTest {

    @InjectMocks
    FlightBusinessLogic flightBusinessLogic;

    @Mock
    FlightService flightService;

    private SearchCriteria criteria;

    private Plane plane;

    private List<Plane> planes;

    @BeforeEach
    void setUp() {
        criteria = SearchCriteria.builder()
                .idArrival(2) //
                .idDeparture(1) //
                .passengerClass(PassengerClass.ECONOMIC) //
                .build();

        plane = Plane.builder()
                .id(1) //
                .speed(950d) //
                .builder("AIRBUS") //
                .model("A330-200") //
                .range(11750.00) //
                .conso(2.4) //
                .isAvailable(true) //
                .build(); //

        planes = new ArrayList<>(Collections.singletonList(plane));
    }

    @Test
    void availableFlights() throws FlightException {

        when(flightService.calculateDistance(criteria.getIdDeparture(), criteria.getIdArrival())).thenReturn(2000.0);
        when(flightService.retrieveplanes()).thenReturn(planes);
        when(flightService.calculatePrice(criteria)).thenReturn(50.0);

        /*
         case Month JULY
         */
        // case friday
        setDate(criteria, Month.JULY, 1);
        ProposedFlight expectedJulyFriday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(PassengerClass.ECONOMIC, expectedJulyFriday.getPassengerClass());
        assertEquals(125.08, expectedJulyFriday.getPrice());
        assertEquals(plane, expectedJulyFriday.getPlane());


        // case tuesday
        setDate(criteria, Month.JULY, 5);
        ProposedFlight expectedJulyTuesday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(118.59, expectedJulyTuesday.getPrice());

        // case sunday
        setDate(criteria, Month.JULY, 3);
        ProposedFlight expectedJulySunday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(121.54, expectedJulySunday.getPrice());

        // case default
        setDate(criteria, Month.JULY, 6);
        ProposedFlight expectedJulyDefault = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(115.64, expectedJulyDefault.getPrice());

        /*
         case Month OCTOBER
        */
        // case friday
        setDate(criteria, Month.OCTOBER, 7);
        ProposedFlight expectedOctoberFriday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(120.44, expectedOctoberFriday.getPrice());

        // case tuesday
        setDate(criteria, Month.OCTOBER, 4);
        ProposedFlight expectedOctoberTuesday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(114.39, expectedOctoberTuesday.getPrice());

        // case sunday
        setDate(criteria, Month.OCTOBER, 2);
        ProposedFlight expectedOctoberSunday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(117.14, expectedOctoberSunday.getPrice());

        // case default
        setDate(criteria, Month.OCTOBER, 5);
        ProposedFlight expectedOctoberDefault = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(111.64, expectedOctoberDefault.getPrice());

        /*
        case Month SEPTEMBER
        */

        // case friday
        setDate(criteria, Month.SEPTEMBER, 2);
        ProposedFlight expectedSeptemberFriday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(114.64, expectedSeptemberFriday.getPrice());

        // case tuesday
        setDate(criteria, Month.SEPTEMBER, 6);
        ProposedFlight expectedSeptemberTuesday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(109.14, expectedSeptemberTuesday.getPrice());

        // case sunday
        setDate(criteria, Month.SEPTEMBER, 4);
        ProposedFlight expectedSeptemberSunday = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(111.64, expectedSeptemberSunday.getPrice());

        // case default
        setDate(criteria, Month.SEPTEMBER, 5);
        ProposedFlight expectedSeptemberDefault = flightBusinessLogic.availableFlights(criteria).get(0);
        assertEquals(106.64, expectedSeptemberDefault.getPrice());
    }

    private void setDate(SearchCriteria criteria, Month month, int day) {
        criteria.setDate(LocalDateTime.of(2022, month.getValue(), day, 12, 30, 30));
    }
}