package org.account.exception;

import org.springframework.dao.DataAccessException;

public class AccountException extends DataAccessException {

    public AccountException(String msg){
        super(msg);
    }
}
