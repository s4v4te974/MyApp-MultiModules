package com.service;

import com.dto.ReservationDto;
import com.entity.Reservation;

public interface ReservationService {

    public Reservation createReservation(ReservationDto dto);

}
