package com.adstore.repository;

import com.adstore.dto.account.AccountStatus;
import com.adstore.dto.account.AccountStoreDto;
import com.adstore.entity.Account;
import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Bezmen. 12.07.2022
 */
public interface AccountRepository {

  Optional<Account> findByLoginOrEmail(String login, String login1);

  Optional<Account> findById(Long id);

  boolean existByEmailOrUserName(String email, String userName);

  Account saveNewSeller(Account accountEntity);

  Optional<Account> findByEmail(String email);

  void updatePasswordByAccountId(Long id, String password);

  List<AccountStoreDto> getAllAccounts();
}
