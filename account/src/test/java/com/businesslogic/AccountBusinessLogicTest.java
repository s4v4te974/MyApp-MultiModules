package com.businesslogic;


import com.entity.Account;
import com.exception.AccountException;
import com.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import static com.utils.AccountConst.DELETE_ERROR;
import static com.utils.AccountConst.PERSIST_ERROR;
import static com.utils.AccountConst.RETRIEVE_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void retrieveAccount() throws AccountException {
        Account account = buildAccount();
        when(businessLogic.retrieveAccount(LOGIN, PASSWORD)).thenReturn(account);
        Account result = businessLogic.retrieveAccount(LOGIN, PASSWORD);
        assertThat(account).isEqualTo(result);
    }

    @Test
    void retrieveAccountException() {
        doThrow(new DataAccessException("Error") {
        }).when(repository).retrieveAccount(LOGIN, PASSWORD);
        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.retrieveAccount(LOGIN, PASSWORD));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(RETRIEVE_ERROR));
    }

    @Test
    void persistAccount() throws AccountException {
        Account account = buildAccount();
        when(businessLogic.persistAccount(account)).thenReturn(account);
        Account result = businessLogic.persistAccount(account);
        assertThat(account).isEqualTo(result);
    }

    @Test
    void persistAccountException() {
        Account account = buildAccount();
        doThrow(new DataAccessException("Error") {
        }).when(repository).save(account);
        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.persistAccount(account));
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

    private Account buildAccount() {
        return Account.builder()
                .id(0) //
                .name("name") //
                .lastName("lastname") //
                .login(LOGIN) //
                .password(PASSWORD) //
                .email("email") //
                .passeport("passeport") //
                .build();
    }
}
