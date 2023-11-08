package com.businesslogic;


import com.dto.AccountRecord;
import com.entity.Account;
import com.enums.Role;
import com.exception.AccountException;
import com.mapper.AccountMapper;
import com.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.utils.AccountConst.DELETE_ERROR;
import static com.utils.AccountConst.DUPLICATION_EXCEPTION;
import static com.utils.AccountConst.PERSIST_ERROR;
import static com.utils.AccountConst.RETRIEVE_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountBusinessLogicTest {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    @InjectMocks
    AccountBusinessLogic businessLogic;
    @Mock
    AccountRepository repository;

    @Mock
    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void retrieveAccount() throws AccountException {
        Account retrievedAccount = buildAccount();
        AccountRecord record = buildRecord();

        when(repository.findByLoginAndPassword(any(String.class), any(String.class)))
                .thenReturn(Optional.of(retrievedAccount));
        when(mapper.mapToRecord(retrievedAccount)).thenReturn(record);

        AccountRecord retrievedAccountRecord = businessLogic.retrieveAccount(LOGIN, PASSWORD);

        assertEquals(retrievedAccount.getId(), retrievedAccountRecord.id());
        assertEquals(retrievedAccount.getName(), retrievedAccountRecord.name());
        assertEquals(retrievedAccount.getLastName(), retrievedAccountRecord.lastName());
        assertEquals(retrievedAccount.getLogin(), retrievedAccountRecord.login());
        assertEquals(retrievedAccount.getPassword(), retrievedAccountRecord.password());
        assertEquals(retrievedAccount.getEmail(), retrievedAccountRecord.email());
        assertEquals(retrievedAccount.getPasseport(), retrievedAccountRecord.passeport());
    }

    @Test
    void retrieveAccountException() {
        doThrow(new DataAccessException("Error") {
        }).when(repository).findByLoginAndPassword(LOGIN, PASSWORD);
        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.retrieveAccount(LOGIN, PASSWORD));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(RETRIEVE_ERROR));
    }

    @Test
    void persistAccount() throws AccountException {

        AccountRecord accountRecord = buildRecord();
        Account account = buildAccount();

        when(mapper.mapToEntity(accountRecord)).thenReturn(account);

        AccountRecord result = businessLogic.persistAccount(accountRecord);

        assertEquals(account.getId(), result.id());
        assertEquals(account.getName(), result.name());
        assertEquals(account.getLastName(), result.lastName());
        assertEquals(account.getLogin(), result.login());
        assertEquals(account.getPassword(), result.password());
        assertEquals(account.getEmail(), result.email());
        assertEquals(account.getPasseport(), result.passeport());
    }

    @Test
    void persistAccountException() {

        AccountRecord record = buildRecord();

        when(repository.findByNameAndLastNameAndEmail(any(), any(), any()))
                .thenReturn(Optional.empty());

        doThrow(new DataAccessException("Error") {})
                .when(repository).save(any());

        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.persistAccount(record));

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(PERSIST_ERROR));
    }

    @Test
    void deleteAccount() throws AccountException {
        businessLogic.deleteAccount(1);
        verify(repository, atLeastOnce()).deleteById(anyInt());
    }

    @Test
    void deleteAccountException() {
        doThrow(new DataAccessException("Error") {
        }).when(repository).deleteById(1);
        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.deleteAccount(1));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(DELETE_ERROR));
    }

    @Test
    void updateAccount() throws AccountException {

        Account account = buildAccount();
        AccountRecord record = buildRecord();
        List<Account> accounts = new ArrayList<>(Collections.singletonList(account));

        when(repository.findById(record.id())).thenReturn(Optional.of(account));
        when(repository.findAllByNameAndLastNameAndEmail(any(String.class), any(String.class), any(String.class)))
                .thenReturn(accounts);
        when(mapper.mapToEntity(record)).thenReturn(account);

        AccountRecord expected = businessLogic.updateAccount(record);

        assertThat(record).isEqualTo(expected);
    }

    @Test
    void updateAccountExceptionDuplication() throws AccountException {

        Account account = buildAccount();
        Account account2 = buildAccount();
        account2.setId(100);

        AccountRecord record = buildRecord();
        List<Account> accounts = new ArrayList<>(Collections.singletonList(account2));

        when(repository.findById(record.id())).thenReturn(Optional.of(account));
        when(repository.findAllByNameAndLastNameAndEmail(any(String.class), any(String.class), any(String.class)))
                .thenReturn(accounts);

        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.updateAccount(record));

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(DUPLICATION_EXCEPTION));
    }

    @Test
    void updateAccountExceptionNotUnique() throws AccountException {

        Account account = buildAccount();
        Account account2 = buildAccount();
        account2.setId(100);

        AccountRecord record = buildRecord();
        List<Account> accounts = new ArrayList<>(Arrays.asList(account, account2));

        when(repository.findById(record.id())).thenReturn(Optional.of(account));
        when(repository.findAllByNameAndLastNameAndEmail(any(String.class), any(String.class), any(String.class)))
                .thenReturn(accounts);

        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.updateAccount(record));

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(DUPLICATION_EXCEPTION));
    }

    @Test
    void updateAccountDataAccessException() throws AccountException {

        Account account = buildAccount();
        AccountRecord record = buildRecord();

        doThrow(new DataAccessException("Error") {})
                .when(repository).findById(any());

        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.updateAccount(record));

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(PERSIST_ERROR));
    }

    private AccountRecord buildRecord(){
        return new AccountRecord(0, //
                "name", //
                "lastname", //
                LOGIN, //
                PASSWORD, //
                "email", //
                "passeport" //
                , Role.ADMIN.name() );
    }

    private Account buildAccount(){
        return Account.builder() //
                .id(0) //
                .name("name") //
                .lastName("lastname") //
                .login(LOGIN) //
                .email("email") //
                .password(PASSWORD) //
                .passeport("passeport") //
                .role(Role.ADMIN.name())
                .build(); //
    }
}
