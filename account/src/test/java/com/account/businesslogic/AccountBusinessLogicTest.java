package com.account.businesslogic;

import org.account.entity.Account;
import org.account.exception.AccountException;
import org.account.repository.AccountRepository;
import org.account.businesslogic.AccountBusinessLogic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.account.utils.AccountConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountBusinessLogicTest {

    @InjectMocks
    AccountBusinessLogic businessLogic;

    @Mock
    AccountBusinessLogic businessLogicMock;

    @Mock
    AccountRepository repository;

    private static final String LOGIN = "login";

    private static final String PASSWORD = "password";

    @Test
    void retrieveAccount(){
       Account account = buildAccount();
       when(businessLogic.retrieveAccount(LOGIN, PASSWORD)).thenReturn(account);
       Account result = businessLogic.retrieveAccount(LOGIN, PASSWORD);
       assertThat(account).isEqualTo(result);
    }

    @Test
    void retrieveAccountException(){
        when(businessLogic.retrieveAccount(LOGIN, PASSWORD)).thenThrow((AccountException.class));
        Exception exception = assertThrows(AccountException.class, () ->
            businessLogic.retrieveAccount(LOGIN, PASSWORD));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(RETRIEVE_ERROR));
    }

    @Test
    void persistAccount(){
        Account account = buildAccount();
        when(businessLogic.persistAccount(account)).thenReturn(account);
        Account result = businessLogic.persistAccount(account);
        assertThat(account).isEqualTo(result);
    }

    @Test
    void persistAccountException(){
        Account account = buildAccount();
        when(businessLogic.persistAccount(account)).thenThrow((AccountException.class));
        Exception exception = assertThrows(AccountException.class,
                () -> businessLogic.persistAccount(account));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(PERSIST_ERROR));
    }

    @Test
    void deleteAccount(){
        businessLogic.deleteAccount(1);
        verify(repository, atLeastOnce()).deleteById(anyInt());
    }

    @Test
    void deleteAccountException(){
        doThrow(new AccountException(DELETE_ERROR)).when(businessLogicMock).deleteAccount(1);
        Exception exception = assertThrows(AccountException.class,
                () -> businessLogicMock.deleteAccount(1));
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains(DELETE_ERROR));
    }

    private Account buildAccount(){
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
