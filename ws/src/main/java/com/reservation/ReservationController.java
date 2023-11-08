package com.reservation;

import com.businesslogic.ReservationBusinessLogic;
import com.dto.ReservationInformationRecord;
import com.dto.ReservationLoginRecord;
import com.dto.ReservationRecord;
import com.exception.ReservationException;
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

import static com.utils.WebServiceConsts.CREATE_RESERVATION;
import static com.utils.WebServiceConsts.DEFAULT_PATH;
import static com.utils.WebServiceConsts.DELETE_RESERVATION;
import static com.utils.WebServiceConsts.RETRIEVE_RESERVATION;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = DEFAULT_PATH)
public class ReservationController {

    private final ReservationBusinessLogic reservationBusinessLogic;

    @GetMapping(value = RETRIEVE_RESERVATION,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationRecord> retrieveReservation
            (@RequestBody ReservationLoginRecord reservationLoginRecord) throws ReservationException {
        ReservationRecord reservationRecord = reservationBusinessLogic.retrieveReservation(reservationLoginRecord);
        return new ResponseEntity<>(reservationRecord, HttpStatus.OK);
    }

    @PostMapping(value = CREATE_RESERVATION,
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ReservationRecord> createReservation
            (@RequestBody ReservationInformationRecord reservationInformationRecord) throws ReservationException {
        ReservationRecord reservationRecord = reservationBusinessLogic.createReservation(reservationInformationRecord);
        return new ResponseEntity<>(reservationRecord, HttpStatus.OK);
    }

    @DeleteMapping(value = DELETE_RESERVATION + "{id}")
    ResponseEntity<String> deleteReservation(@PathVariable("id") Integer id) throws ReservationException {
        reservationBusinessLogic.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
