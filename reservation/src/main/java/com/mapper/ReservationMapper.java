package com.mapper;

import com.dto.ReservationInformationRecord;
import com.dto.ReservationRecord;
import com.entity.Account;
import com.entity.Plane;
import com.entity.Reservation;
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
