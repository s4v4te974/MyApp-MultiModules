package com.ws.reservation;

import com.reservation.exception.ReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.reservation.utils.ReservationConsts.UNABLE_TO_DELETE_RESERVATION;
import static com.reservation.utils.ReservationConsts.UNABLE_TO_RETRIEVE_RESERVATION;
import static com.reservation.utils.ReservationConsts.UNABLE_TO_SAVE_RESERVATION;

@ControllerAdvice
public class ReservationControllerAdvice {

    @ExceptionHandler(value = ReservationException.class)
    public ResponseEntity<Object> reservationController(ReservationException ex){
        if(ex.getClass().getName().contains(UNABLE_TO_RETRIEVE_RESERVATION)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        if(ex.getClass().getName().contains(UNABLE_TO_SAVE_RESERVATION)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(ex.getClass().getName().contains(UNABLE_TO_DELETE_RESERVATION)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}