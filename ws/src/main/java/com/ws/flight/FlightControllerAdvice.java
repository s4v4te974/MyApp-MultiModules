package com.ws.flight;

import com.account.exception.AccountException;
import com.flight.exception.FlightException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.flight.utils.FlightConsts.UNABLE_TO_CALCULATE_PRICE;
import static com.flight.utils.FlightConsts.UNABLE_TO_FIND_PLANE;
import static com.flight.utils.FlightConsts.UNABLE_TO_RETRIEVE_CITIES;

@RestControllerAdvice
public class FlightControllerAdvice {

    @ExceptionHandler(value = FlightException.class)
    public ResponseEntity<Object> flightRestController(AccountException ex) {
        if(ex.getClass().getName().contains(UNABLE_TO_RETRIEVE_CITIES)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        if(ex.getClass().getName().contains(UNABLE_TO_CALCULATE_PRICE)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(ex.getClass().getName().contains(UNABLE_TO_FIND_PLANE)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return null;
    }
}
