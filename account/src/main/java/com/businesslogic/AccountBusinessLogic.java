package com.businesslogic;

import com.dto.AccountRecord;
import com.entity.Account;
import com.exception.AccountException;
import com.mapper.AccountMapper;
import com.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.utils.AccountConst.DELETE_ERROR;
import static com.utils.AccountConst.PERSIST_ERROR;
import static com.utils.AccountConst.RETRIEVE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBusinessLogic {

    private final AccountRepository accountRepository;

    private final AccountMapper mapper;

    public AccountRecord retrieveAccount(String login, String password) throws AccountException {
        AccountRecord account = null;
        try {
            Account existingAccount = accountRepository.findByLoginAndPassword(login, password).orElse(null);
            if(existingAccount != null){
                account = mapper.mapToRecord(existingAccount);
            }
        } catch (DataAccessException dae) {
            throw new AccountException(RETRIEVE_ERROR);
        }
        return account;
    }

    public AccountRecord persistAccount(AccountRecord accountRecord) throws AccountException {
        Optional<Account> existingAccount = accountRepository.findByNameAndLastNameAndEmail(
                accountRecord.name(), accountRecord.lastName(), accountRecord.email());

        if(existingAccount.isEmpty()){
            try {
                accountRepository.save(mapper.mapToEntity(accountRecord));
            } catch (DataAccessException dae) {
                throw new AccountException(PERSIST_ERROR);
            }
        }
        return accountRecord;
    }

    public AccountRecord updateAccount(AccountRecord accountToUpdate) throws AccountException {
        try {
            Account retrievedAccount = accountRepository.findById(accountToUpdate.id()).orElse(null);

            List<Account> existingAccounts = accountRepository.findAllByNameAndLastNameAndEmail(
                    accountToUpdate.name(), accountToUpdate.lastName(), accountToUpdate.email());

            if (retrievedAccount != null && existingAccounts.size() < 2){
                if(retrievedAccount.getId() == existingAccounts.get(0).getId()){
                    accountRepository.save(mapper.mapToEntity(accountToUpdate));
                }
                return accountToUpdate;
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