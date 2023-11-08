package com.businesslogic;

import com.dto.ReservationInformationRecord;
import com.dto.ReservationLoginRecord;
import com.dto.ReservationRecord;
import com.entity.Reservation;
import com.exception.ReservationException;
import com.mapper.ReservationMapper;
import com.repository.ReservationRepository;
import com.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import static com.utils.ReservationConsts.UNABLE_TO_DELETE_RESERVATION;
import static com.utils.ReservationConsts.UNABLE_TO_RETRIEVE_RESERVATION;
import static com.utils.ReservationConsts.UNABLE_TO_SAVE_RESERVATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationBusinessLogic {

    private final ReservationRepository reservationRepository;

    private final ReservationService reservationService;

    private final ReservationMapper mapper;

    public ReservationRecord retrieveReservation(ReservationLoginRecord reservationLoginRecord) throws ReservationException {
        log.info("Start retrieve reservation");
        try{
            Reservation reservation  = reservationRepository.findByReferenceAndLogin(
                    reservationLoginRecord.reference(), reservationLoginRecord.login());
            log.info("Returning the retrieved reservation");
            return mapper.mapToRecord(reservation);
        }catch (DataAccessException dae){
            throw new ReservationException(UNABLE_TO_RETRIEVE_RESERVATION);
        }
    }

    public ReservationRecord createReservation(ReservationInformationRecord infoRecord) throws ReservationException {
        log.info("Start creating a reservation");
        try{
            return reservationService.createReservation(infoRecord);
        }catch (DataAccessException dae){
            throw new ReservationException(UNABLE_TO_SAVE_RESERVATION);
        }
    }

    public void deleteReservation(int id) throws ReservationException {
        log.info("Start deleting reservation");
        try{
            reservationRepository.deleteById(id);
        }catch (DataAccessException dae){
            throw new ReservationException(UNABLE_TO_DELETE_RESERVATION);
        }
    }
}
