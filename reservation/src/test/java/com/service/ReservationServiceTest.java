package com.service;

import com.dto.ReservationInformationRecord;
import com.dto.ReservationRecord;
import com.entity.Account;
import com.entity.Plane;
import com.entity.Reservation;
import com.mapper.ReservationMapper;
import com.repository.AccountRepository;
import com.repository.PlaneRepository;
import com.repository.ReservationRepository;
import com.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationServiceImpl service;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    PlaneRepository planeRepository;

    @Mock
    ReservationMapper mapper = Mappers.getMapper(ReservationMapper.class);

    @Test
    void generateReferencesTest(){
        String generatedRef = service.generateReferences();
        assertNotNull(generatedRef);
        assertTrue(generatedRef.length() > 5);
    }

    @Test
    void createReservationTest(){

        ReservationInformationRecord reservationInformationRecord = new ReservationInformationRecord(
                "reference", 1, 2, "20.00", "ECONOMIC");

        ReservationRecord reservationRecord = new ReservationRecord(
                1, "ref", "name", "lastName", "passeport",
                "A320-200", "22.00", "login", "ECONOMICS");

        Account account = Account.builder() //
                .id(1).name("name").lastName("lastName").passeport("passeport").login("login") //
                .build(); //

        Plane plane = Plane.builder() //
                .id(1).manufacturer("Airbus") //
                .model("A320-200").build(); //

        Reservation reservation1 = Reservation.builder().reference("ref1").build();
        Reservation reservation2 = Reservation.builder().reference("ref2").build();
        Reservation reservation3 = Reservation.builder().reference("ref3").build();

        when(reservationRepository.findAll())
                .thenReturn(new ArrayList<>(Arrays.asList(reservation1, reservation2, reservation3)));

        when(accountRepository.findById(reservationInformationRecord.user()))
                .thenReturn(Optional.of(account));

        when(planeRepository.findById(2)).thenReturn(Optional.of(plane));

        when(mapper.mapToRecordFromMultipleSource(plane, account, reservationInformationRecord))
                .thenReturn(reservationRecord);

        ReservationRecord reservation = service.createReservation(reservationInformationRecord);

        assertEquals(reservationRecord.id(), reservation.id());
        assertEquals(reservationRecord.reference(), reservation.reference());
        assertEquals(reservationRecord.name(), reservation.name());
        assertEquals(reservationRecord.lastName(), reservation.lastName());
        assertEquals(reservationRecord.passeport(), reservation.passeport());
        assertEquals(reservationRecord.planeRef(), reservation.planeRef());
        assertEquals(reservationRecord.price(), reservation.price());
        assertEquals(reservationRecord.login(), reservation.login());
        assertEquals(reservationRecord.passengerClass(), reservation.passengerClass());
    }
}
