package com.account;

import com.exception.AccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.utils.AccountConst.DELETE_ERROR;
import static com.utils.AccountConst.DUPLICATION_EXCEPTION;
import static com.utils.AccountConst.PERSIST_ERROR;
import static com.utils.AccountConst.RETRIEVE_ERROR;

@RestControllerAdvice
public class AccountControllerAdvice {

    @ExceptionHandler(value = {AccountException.class})
    public ResponseEntity<Object> accountRestController(AccountException ex) {
        if(ex.getClass().getName().contains(DELETE_ERROR)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        if(ex.getClass().getName().contains(PERSIST_ERROR)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        if(ex.getClass().getName().contains(RETRIEVE_ERROR)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        if(ex.getClass().getName().contains(DUPLICATION_EXCEPTION)){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        return null;
    }
}
