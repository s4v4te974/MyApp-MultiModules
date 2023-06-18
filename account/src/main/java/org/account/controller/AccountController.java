package org.account.controller;

import org.account.businesslogic.AccountBusinessLogic;
import org.account.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.account.utils.AccountConst.*;
import static org.account.utils.AccountConst.DEFAULT_PATH;

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
    ResponseEntity<Account> retrieveAccount(@RequestBody Account account) {
        Account retrievedAccount = accountBusinessLogic.retrieveAccount(account.getLogin(), account.getPassword());
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @PostMapping(value = CREATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account retrievedAccount = accountBusinessLogic.persistAccount(account);
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @PutMapping(value = UPDATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account retrievedAccount = accountBusinessLogic.persistAccount(account);
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = DELETE_USER + "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Integer id) {
        accountBusinessLogic.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
