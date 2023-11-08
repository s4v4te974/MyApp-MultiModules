package com.mapper;

import com.dto.ReservationInformationRecord;
import com.dto.ReservationRecord;
import com.entity.Account;
import com.entity.Plane;
import com.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReservationMapperTest {

    ReservationMapper mapper = Mappers.getMapper(ReservationMapper.class);

    @Test
    void mapToRecordFromMultipleSourceTest() {

        ReservationInformationRecord dto = new ReservationInformationRecord( "ref",
                1, 2 , "22.0", "ECONOMIC");

        Plane plane = Plane.builder() //
                .model("A330-200") //
                .build(); //

        Account account = Account.builder() //
                .name("name") //
                .lastName("lastName") //
                .passeport("passeport") //
                .login("login") //
                .build(); //

        ReservationRecord reservation = mapper.mapToRecordFromMultipleSource(plane, account, dto);

        assertEquals(plane.getModel(), reservation.planeRef());
        assertEquals(dto.passengerClass(), reservation.passengerClass());
        assertEquals(dto.price(), reservation.price());
        assertEquals(account.getName(), reservation.name());
        assertEquals(account.getLastName(), reservation.lastName());
        assertEquals(account.getPasseport(), reservation.passeport());
        assertEquals(account.getLogin(), reservation.login());

    }

    @Test
    void mapToEntityTest(){
        ReservationRecord reservationRecord = buildReservationRecord();
        Reservation reservation = mapper.mapToEntity(reservationRecord);

        assertEquals(reservationRecord.id(), reservation.getId());
        assertEquals(reservationRecord.reference(), reservation.getReference());
        assertEquals(reservationRecord.planeRef(), reservation.getPlaneRef());
        assertEquals(reservationRecord.name(), reservation.getName());
        assertEquals(reservationRecord.lastName(), reservation.getLastName());
        assertEquals(reservationRecord.passeport(), reservation.getPasseport());
        assertEquals(reservationRecord.login(), reservation.getLogin());
        assertEquals(reservationRecord.price(), reservation.getPrice());
        assertEquals(reservationRecord.passengerClass(), reservation.getPassengerClass());
    }

    @Test
    void mapToRecord(){
        Reservation reservation = buildReservation();
        ReservationRecord reservationRecord = mapper.mapToRecord(reservation);

        assertEquals(reservationRecord.id(), reservation.getId());
        assertEquals(reservationRecord.reference(), reservation.getReference());
        assertEquals(reservationRecord.planeRef(), reservation.getPlaneRef());
        assertEquals(reservationRecord.name(), reservation.getName());
        assertEquals(reservationRecord.lastName(), reservation.getLastName());
        assertEquals(reservationRecord.passeport(), reservation.getPasseport());
        assertEquals(reservationRecord.login(), reservation.getLogin());
        assertEquals(reservationRecord.price(), reservation.getPrice());
        assertEquals(reservationRecord.passengerClass(), reservation.getPassengerClass());
    }

    Reservation buildReservation(){
        return Reservation.builder() //
                .id(1) //
                .reference("MW8KT") //
                .planeRef("A330-200") //
                .name("name") //
                .lastName("lastName") //
                .price("22.00") //
                .passeport("passeport") //
                .login("login") //
                .passengerClass("ECONOMIC")
                .build(); //
    }

    ReservationRecord buildReservationRecord(){
        return new ReservationRecord(
                1, //
                "MW8KT", //
                "name", //
                "lastName", //
                "passeport", //
                "A330-200", //
                "22.00", //
                "login", //
                "ECONOMIC");
    }
}
