package com.account;

import com.businesslogic.AccountBusinessLogic;
import com.dto.AccountRecord;
import com.exception.AccountException;
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

import static com.utils.WebServiceConsts.CREATE_USER;
import static com.utils.WebServiceConsts.DEFAULT_PATH;
import static com.utils.WebServiceConsts.DELETE_USER;
import static com.utils.WebServiceConsts.RETRIEVE_USER;
import static com.utils.WebServiceConsts.UPDATE_USER;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = DEFAULT_PATH)
public class AccountController {

    private AccountBusinessLogic accountBusinessLogic;

    @GetMapping(value = RETRIEVE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AccountRecord> retrieveAccount(@RequestBody AccountRecord account) throws AccountException {
        AccountRecord retrievedAccount = accountBusinessLogic.retrieveAccount(account.login(), account.password());
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @PostMapping(value = CREATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AccountRecord> createAccount(@RequestBody AccountRecord account) throws AccountException {
        AccountRecord retrievedAccount = accountBusinessLogic.persistAccount(account);
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @PutMapping(value = UPDATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AccountRecord> updateAccount(@RequestBody AccountRecord account) throws AccountException {
        AccountRecord retrievedAccount = accountBusinessLogic.persistAccount(account);
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = DELETE_USER + "{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Integer id) throws AccountException {
        accountBusinessLogic.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
