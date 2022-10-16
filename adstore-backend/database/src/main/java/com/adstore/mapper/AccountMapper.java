package com.adstore.mapper;

import com.adstore.dto.account.AccountDto;
import com.adstore.dto.account.AccountStoreDto;
import com.adstore.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

  public AccountDto toDto(Account accountEntity) {
    if (accountEntity == null) {
      return null;
    }
    final AccountDto accountDto = new AccountDto();
    accountDto.setId(accountEntity.getId());
    accountDto.setEmail(accountEntity.getEmail());
    accountDto.setRole(accountEntity.getRole());
    accountDto.setUserName(accountEntity.getUserName());
    accountDto.setFirstName(accountEntity.getFirstName());
    accountDto.setLastName(accountEntity.getLastName());
    return accountDto;
  }

  public AccountStoreDto toAccountStoreDto(Account accountEntity) {
    if (accountEntity == null) {
      return null;
    }
    final AccountStoreDto accountDto = new AccountStoreDto(
        accountEntity.getId(), accountEntity.getFirstName(), accountEntity.getUserName(),
        accountEntity.getLastName(), accountEntity.getEmail(), accountEntity.getRole(),
        accountEntity.isHasStore()
    );
    return accountDto;
  }
}
