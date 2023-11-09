package com.reservation.mapper;

import com.reservation.dto.ReservationInformationRecord;
import com.reservation.dto.ReservationRecord;
import com.account.entity.Account;
import com.flight.entity.Plane;
import com.reservation.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface ReservationMapper {

    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "planeRef", source = "plane.model")
    @Mapping(target = "passengerClass", source = "dto.passengerClass")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "name", source = "account.name")
    @Mapping(target = "lastName", source = "account.lastName")
    @Mapping(target = "passeport", source = "account.passeport")
    @Mapping(target = "login", source = "account.login")
    ReservationRecord mapToRecordFromMultipleSource(Plane plane, Account account, ReservationInformationRecord dto);
    
    Reservation mapToEntity(ReservationRecord reservationRecord);

    ReservationRecord mapToRecord(Reservation reservation);

}
