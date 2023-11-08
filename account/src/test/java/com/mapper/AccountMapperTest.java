package com.mapper;

import com.dto.AccountRecord;
import com.entity.Account;
import com.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {

    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void mapToEntity(){
        AccountRecord accountToMap = new AccountRecord(1, //
                "Joseph", //
                "Staline", //
                "Jstal", //
                "Kremlin88", //
                "Joseph.staline@kremlin.ru",//
                "123RU456",
                Role.ADMIN.name());

        Account expected = mapper.mapToEntity(accountToMap);
        assertEquals(accountToMap.id(), expected.getId());
        assertEquals(accountToMap.name(), expected.getName());
        assertEquals(accountToMap.lastName(), expected.getLastName());
        assertEquals(accountToMap.login(), expected.getLogin());
        assertEquals(accountToMap.password(), expected.getPassword());
        assertEquals(accountToMap.email(), expected.getEmail());
        assertEquals(accountToMap.passeport(), expected.getPasseport());
    }

    @Test
    void mapToRecord(){
        Account accountToMap = Account.builder()
                .id(1) //
                .name("Joseph") //
                .lastName("Staline") //
                .login("Jstal") //
                .password("Kremlin88") //
                .email("Joseph.staline@kremlin.ru")//
                .passeport("123RU456") //
                .role(Role.ADMIN.name()) //
                .build();

        AccountRecord expected = mapper.mapToRecord(accountToMap);
        assertEquals(expected.id(), accountToMap.getId());
        assertEquals(expected.name(), accountToMap.getName());
        assertEquals(expected.lastName(), accountToMap.getLastName());
        assertEquals(expected.login(), accountToMap.getLogin());
        assertEquals(expected.password(), accountToMap.getPassword());
        assertEquals(expected.email(), accountToMap.getEmail());
        assertEquals(expected.passeport(), accountToMap.getPasseport());
    }
}
