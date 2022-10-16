package com.adstore.repository.impl;

import static com.adstore.jooq.Tables.ACCOUNT_PASSWORD_RESET_TOKEN;

import com.adstore.entity.VerificationToken;
import com.adstore.repository.AccountPasswordResetTokenRepository;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Bezmen. 14.07.2022
 */
@Repository
public class AccountPasswordResetTokenRepositoryImpl implements
    AccountPasswordResetTokenRepository {

  private final DSLContext create;

  public AccountPasswordResetTokenRepositoryImpl(final DSLContext create) {
    this.create = create;
  }

  @Override
  public void saveResetTokenForUser(Long accountId, String token) {
    final OffsetDateTime expiryAt = OffsetDateTime.now().plus(23, ChronoUnit.HOURS);
    create.insertInto(ACCOUNT_PASSWORD_RESET_TOKEN)
        .set(ACCOUNT_PASSWORD_RESET_TOKEN.ACCOUNT_ID, accountId)
        .set(ACCOUNT_PASSWORD_RESET_TOKEN.TOKEN, token)
        .set(ACCOUNT_PASSWORD_RESET_TOKEN.EXPIRY_AT, expiryAt)
        .execute();
  }

  @Override
  public VerificationToken findByToken(@NotNull String token) {
    return Objects.requireNonNull(
        create.select(ACCOUNT_PASSWORD_RESET_TOKEN.ACCOUNT_ID, ACCOUNT_PASSWORD_RESET_TOKEN.TOKEN,
                ACCOUNT_PASSWORD_RESET_TOKEN.EXPIRY_AT)
            .from(ACCOUNT_PASSWORD_RESET_TOKEN)
            .where(ACCOUNT_PASSWORD_RESET_TOKEN.TOKEN.eq(token))
            .fetchOne()).map(record -> {
          VerificationToken tokenResult = new VerificationToken();
          tokenResult.setToken(record.get(ACCOUNT_PASSWORD_RESET_TOKEN.TOKEN));
          tokenResult.setAccountId(record.get(ACCOUNT_PASSWORD_RESET_TOKEN.ACCOUNT_ID));
          tokenResult.setExpiryAt(
              record.get(ACCOUNT_PASSWORD_RESET_TOKEN.EXPIRY_AT).toInstant());
          return tokenResult;
        }
    );
  }

  @Override
  public void deleteVerificationTokenByAccountIdAndToken(Long accountId, String token) {
    create.delete(ACCOUNT_PASSWORD_RESET_TOKEN)
        .where(ACCOUNT_PASSWORD_RESET_TOKEN.ACCOUNT_ID.eq(accountId)
            .and(ACCOUNT_PASSWORD_RESET_TOKEN.TOKEN.eq(token)))
        .execute();
  }
}
