package com.reservation.service;

import com.reservation.dto.ReservationInformationRecord;
import com.reservation.dto.ReservationRecord;

public interface ReservationService {

    ReservationRecord createReservation(ReservationInformationRecord reservationInformationRecord);

}
