package com.businesslogic;

import com.dto.ReservationDto;
import com.dto.RetrieveReservationDto;
import com.entity.Reservation;
import com.exception.ReservationException;
import com.repository.ReservationRepository;
import com.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import static com.utils.ReservationConsts.UNABLE_TO_DELETE_RESERVATION;
import static com.utils.ReservationConsts.UNABLE_TO_RETRIEVE_RESERVATION;
import static com.utils.ReservationConsts.UNABLE_TO_SAVE_RESERVATION;

@Service
@RequiredArgsConstructor
public class ReservationBusinessLogic {

    private final ReservationRepository reservationRepository;

    private final ReservationService reservationService;

    public Reservation retrieveReservation(RetrieveReservationDto dto) throws ReservationException {
        try{
            return reservationRepository.retrieveReservationByReference(dto.getReference(), dto.getLogin());
        }catch (DataAccessException dae){
            throw new ReservationException(UNABLE_TO_RETRIEVE_RESERVATION);
        }
    }

    public Reservation createReservation(ReservationDto dto) throws ReservationException {
        try{
            return reservationService.createReservation(dto);
        }catch (DataAccessException dae){
            throw new ReservationException(UNABLE_TO_SAVE_RESERVATION);
        }

    }

    public void deleteReservation(int id) throws ReservationException {
        try{
            reservationRepository.deleteById(id);
        }catch (DataAccessException dae){
            throw new ReservationException(UNABLE_TO_DELETE_RESERVATION);
        }
    }
}
