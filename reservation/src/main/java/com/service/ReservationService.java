package com.service;

import com.dto.ReservationInformationRecord;
import com.dto.ReservationRecord;

public interface ReservationService {

    ReservationRecord createReservation(ReservationInformationRecord reservationInformationRecord);

}
