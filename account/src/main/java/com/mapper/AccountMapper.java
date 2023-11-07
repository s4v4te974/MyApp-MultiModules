package com.mapper;

import com.dto.AccountRecord;
import com.entity.Account;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper()
public interface AccountMapper {

    Account mapToEntity(AccountRecord accountRecord);

    AccountRecord mapToRecord(Account account);
}
