package com.mapper;

import com.dto.ReservationDto;
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

    @Test
    void mapToReservation() {

        ReservationMapper mapper = Mappers.getMapper(ReservationMapper.class);
        ReservationDto dto = ReservationDto.builder() //
                .passengerClass("ECONOMIC") //
                .price("22.0") //
                .build(); //

        Plane plane = Plane.builder() //
                .model("A330-200") //
                .build(); //

        Account account = Account.builder() //
                .name("name") //
                .lastName("lastName") //
                .passeport("passeport") //
                .login("login") //
                .build(); //

        Reservation reservation = mapper.mapToReservation(plane, account, dto);

        assertEquals(plane.getModel(), reservation.getPlaneRef());
        assertEquals(dto.getPassengerClass(), reservation.getPassengerClass());
        assertEquals(dto.getPrice(), reservation.getPrice());
        assertEquals(account.getName(), reservation.getName());
        assertEquals(account.getLastName(), reservation.getLastName());
        assertEquals(account.getPasseport(), reservation.getPasseport());
        assertEquals(account.getLogin(), reservation.getLogin());

    }


}
