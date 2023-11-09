package com.account.mapper;

import com.account.entity.Account;
import com.account.dto.AccountRecord;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper()
public interface AccountMapper {

    Account mapToEntity(AccountRecord accountRecord);

    AccountRecord mapToRecord(Account account);
}
