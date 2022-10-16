package com.adstore.repository.impl;

import static com.adstore.jooq.tables.AccountVerification.ACCOUNT_VERIFICATION;

import com.adstore.dto.account.AccountStatus;
import com.adstore.entity.VerificationToken;
import com.adstore.jooq.enums.AccountVerificationStatus;
import com.adstore.repository.AccountVerificationRepository;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class AccountVerificationRepositoryImpl implements AccountVerificationRepository {

  private final DSLContext create;

  public AccountVerificationRepositoryImpl(final DSLContext create) {
    this.create = create;
  }

  @Override
  public void createVerificationToken(Long accountId, String token) {
    final OffsetDateTime expiryAt = OffsetDateTime.now().plus(23, ChronoUnit.HOURS);
    create.insertInto(ACCOUNT_VERIFICATION)
        .set(ACCOUNT_VERIFICATION.ACCOUNT_ID, accountId)
        .set(ACCOUNT_VERIFICATION.TOKEN, token)
        .set(ACCOUNT_VERIFICATION.EXPIRY_AT, expiryAt)
        .set(ACCOUNT_VERIFICATION.STATUS, AccountVerificationStatus.INITIALIZED)
        .execute();
  }

  @Override
  public VerificationToken findByToken(@NotNull String token) {
    return create.select(ACCOUNT_VERIFICATION.ACCOUNT_ID, ACCOUNT_VERIFICATION.TOKEN,
            ACCOUNT_VERIFICATION.EXPIRY_AT, ACCOUNT_VERIFICATION.STATUS)
        .from(ACCOUNT_VERIFICATION)
        .where(
            ACCOUNT_VERIFICATION.TOKEN.eq(token)
                .and(ACCOUNT_VERIFICATION.STATUS.eq(AccountVerificationStatus.INITIALIZED)))
        .fetchOne().map(record -> {
              VerificationToken tokenResult = new VerificationToken();
              tokenResult.setToken(record.get(ACCOUNT_VERIFICATION.TOKEN));
              tokenResult.setAccountId(record.get(ACCOUNT_VERIFICATION.ACCOUNT_ID));
              tokenResult.setVerified(
                  record.get(ACCOUNT_VERIFICATION.STATUS) == AccountVerificationStatus.VERIFIED);
              tokenResult.setExpiryAt(
                  record.get(ACCOUNT_VERIFICATION.EXPIRY_AT).toInstant());
              return tokenResult;
            }
        );
  }

  @Override
  public void verifyAccount(long accountId, @NotNull String token) {
    create.update(ACCOUNT_VERIFICATION)
        .set(ACCOUNT_VERIFICATION.VERIFIED_ON, OffsetDateTime.now())
        .set(ACCOUNT_VERIFICATION.STATUS, AccountVerificationStatus.VERIFIED)
        .where(ACCOUNT_VERIFICATION.ACCOUNT_ID.eq(accountId)
            .and(ACCOUNT_VERIFICATION.TOKEN.eq(token)))
        .execute();
  }

  @Override
  public void updateAccountStatus(@NotNull AccountStatus newStatus) {
    create.update(ACCOUNT_VERIFICATION)
        .set(ACCOUNT_VERIFICATION.VERIFIED_ON, OffsetDateTime.now())
        .set(ACCOUNT_VERIFICATION.STATUS, newStatus.newStatus())
        .where(ACCOUNT_VERIFICATION.ACCOUNT_ID.eq(newStatus.accountId()))
        .execute();
  }
}
