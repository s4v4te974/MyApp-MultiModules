package com.ws.account;

import com.account.businesslogic.AccountBusinessLogic;
import com.account.dto.AccountRecord;
import com.account.exception.AccountException;
import com.ws.utils.WebServiceConsts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = WebServiceConsts.DEFAULT_PATH)
public class AccountController {

    private AccountBusinessLogic accountBusinessLogic;

    @GetMapping(value = WebServiceConsts.RETRIEVE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AccountRecord> retrieveAccount(@RequestBody AccountRecord account) throws AccountException {
        AccountRecord retrievedAccount = accountBusinessLogic.retrieveAccount(account.login(), account.password());
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @PostMapping(value = WebServiceConsts.CREATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AccountRecord> createAccount(@RequestBody AccountRecord account) throws AccountException {
        AccountRecord retrievedAccount = accountBusinessLogic.persistAccount(account);
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @PutMapping(value = WebServiceConsts.UPDATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AccountRecord> updateAccount(@RequestBody AccountRecord account) throws AccountException {
        AccountRecord retrievedAccount = accountBusinessLogic.persistAccount(account);
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = WebServiceConsts.DELETE_USER + "{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Integer id) throws AccountException {
        accountBusinessLogic.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
