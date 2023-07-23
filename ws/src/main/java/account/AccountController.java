package account;

import businesslogic.AccountBusinessLogic;
import entity.Account;
import exception.AccountException;
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
import utils.AccountConst;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = AccountConst.DEFAULT_PATH)
public class AccountController {

    private AccountBusinessLogic accountBusinessLogic;

    @GetMapping(value = AccountConst.RETRIEVE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Account> retrieveAccount(@RequestBody Account account) throws AccountException {
        Account retrievedAccount = accountBusinessLogic.retrieveAccount(account.getLogin(), account.getPassword());
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @PostMapping(value = AccountConst.CREATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Account> createAccount(@RequestBody Account account) throws AccountException {
        Account retrievedAccount = accountBusinessLogic.persistAccount(account);
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @PutMapping(value = AccountConst.UPDATE_USER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Account> updateAccount(@RequestBody Account account) throws AccountException {
        Account retrievedAccount = accountBusinessLogic.persistAccount(account);
        return new ResponseEntity<>(retrievedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = AccountConst.DELETE_USER + "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Integer id) throws AccountException {
        accountBusinessLogic.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
