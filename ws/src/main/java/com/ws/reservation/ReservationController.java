package com.ws.reservation;

import com.reservation.businesslogic.ReservationBusinessLogic;
import com.reservation.dto.ReservationInformationRecord;
import com.reservation.dto.ReservationLoginRecord;
import com.reservation.dto.ReservationRecord;
import com.reservation.exception.ReservationException;
import com.ws.utils.WebServiceConsts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = WebServiceConsts.DEFAULT_PATH)
public class ReservationController {

    private final ReservationBusinessLogic reservationBusinessLogic;

    @GetMapping(value = WebServiceConsts.RETRIEVE_RESERVATION,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationRecord> retrieveReservation
            (@RequestBody ReservationLoginRecord reservationLoginRecord) throws ReservationException {
        ReservationRecord reservationRecord = reservationBusinessLogic.retrieveReservation(reservationLoginRecord);
        return new ResponseEntity<>(reservationRecord, HttpStatus.OK);
    }

    @PostMapping(value = WebServiceConsts.CREATE_RESERVATION,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ReservationRecord> createReservation
            (@RequestBody ReservationInformationRecord reservationInformationRecord) throws ReservationException {
        ReservationRecord reservationRecord = reservationBusinessLogic.createReservation(reservationInformationRecord);
        return new ResponseEntity<>(reservationRecord, HttpStatus.OK);
    }

    @DeleteMapping(value = WebServiceConsts.DELETE_RESERVATION + "{id}")
    ResponseEntity<String> deleteReservation(@PathVariable("id") Integer id) throws ReservationException {
        reservationBusinessLogic.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
