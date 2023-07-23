package org.account.businesslogic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account.entity.Account;
import org.account.exception.AccountException;
import org.account.repository.AccountRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import static org.account.utils.AccountConst.DELETE_ERROR;
import static org.account.utils.AccountConst.PERSIST_ERROR;
import static org.account.utils.AccountConst.RETRIEVE_ERROR;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBusinessLogic {

    private final AccountRepository accountRepository;

    public Account retrieveAccount(String login, String password) throws AccountException {
        try {
            return accountRepository.retrieveAccount(login, password);
        } catch (DataAccessException dae) {
            throw new AccountException(RETRIEVE_ERROR);
        }
    }

    public Account persistAccount(Account account) throws AccountException {
        try {
            accountRepository.save(account);
            return account;
        } catch (DataAccessException dae) {
            throw new AccountException(PERSIST_ERROR);
        }
    }

    public Account updateAccount(Account accountToUpdate) throws AccountException {
        try {
            Account account = accountRepository.findById(accountToUpdate.getId()).orElse(null);
            if (account != null) {
                account.setName(accountToUpdate.getName());
                account.setLastName(accountToUpdate.getLastName());
                account.setEmail(accountToUpdate.getEmail());
                account.setLogin(accountToUpdate.getLogin());
                account.setPassword(accountToUpdate.getPassword());
                account.setPasseport(accountToUpdate.getPasseport());
                accountRepository.save(account);
                return account;
            } else {
                throw new AccountException(PERSIST_ERROR);
            }
        } catch (DataAccessException dae) {
            throw new AccountException(PERSIST_ERROR);
        }
    }

    public void deleteAccount(Integer id) throws AccountException {
        try {
            accountRepository.deleteById(id);
        } catch (DataAccessException dae) {
            throw new AccountException(DELETE_ERROR);
        }
    }
}