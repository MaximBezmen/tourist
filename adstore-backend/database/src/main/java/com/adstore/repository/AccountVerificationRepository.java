package com.adstore.repository;

import com.adstore.dto.account.AccountStatus;
import com.adstore.entity.VerificationToken;

public interface AccountVerificationRepository {

  void createVerificationToken(Long accountId, String token);

  VerificationToken findByToken(String token);

  void verifyAccount(long accountId, String token);

  void updateAccountStatus(AccountStatus newStatus);
}
