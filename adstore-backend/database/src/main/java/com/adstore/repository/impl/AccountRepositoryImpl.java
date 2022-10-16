package com.adstore.repository.impl;

import static com.adstore.jooq.tables.Account.ACCOUNT;
import static com.adstore.jooq.tables.AccountVerification.ACCOUNT_VERIFICATION;
import static com.adstore.jooq.tables.Role.ROLE;
import static com.adstore.jooq.tables.Store.STORE;
import static org.jooq.impl.DSL.exists;

import com.adstore.dto.account.AccountStatus;
import com.adstore.dto.account.AccountStoreDto;
import com.adstore.entity.Account;
import com.adstore.jooq.enums.AccountVerificationStatus;
import com.adstore.repository.AccountRepository;
import com.adstore.types.RoleType;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Bezmen. 12.07.2022
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {

  private final DSLContext create;

  public AccountRepositoryImpl(final DSLContext create) {
    this.create = create;
  }

  @Override
  public Optional<Account> findByLoginOrEmail(String userName, String email) {
    var result = create.select(ACCOUNT.EMAIL, ACCOUNT.USER_NAME,
            ACCOUNT.ID, ROLE.ROLE_NAME, ACCOUNT_VERIFICATION.STATUS, ACCOUNT.PASSWORD)
        .from(ACCOUNT)
        .leftJoin(ACCOUNT_VERIFICATION)
        .on(ACCOUNT.ID.eq(ACCOUNT_VERIFICATION.ACCOUNT_ID))
        .leftJoin(ROLE)
        .on(ACCOUNT.ROLE_ID.eq(ROLE.ID))
        .where(ACCOUNT.USER_NAME.eq(userName).or(ACCOUNT.EMAIL.eq(email)))
        .fetchOne();

    if (result == null) {
      return Optional.empty();
    }

    final Account account = new Account();
    account.setVerified(
        result.get(ACCOUNT_VERIFICATION.STATUS) == AccountVerificationStatus.VERIFIED);
    account.setRole(RoleType.valueOf(result.get(ROLE.ROLE_NAME)));
    account.setId(result.get(ACCOUNT.ID));
    account.setUserName(result.get(ACCOUNT.USER_NAME));
    account.setEmail(result.get(ACCOUNT.EMAIL));
    account.setPassword(result.get(ACCOUNT.PASSWORD));
    return Optional.of(account);
  }

  @Override
  public Optional<Account> findById(Long id) {
    var hasStore = DSL.field(exists(create.select(STORE.ID)
        .from(STORE)
        .where(STORE.ACCOUNT_ID.eq(id))));

    var result = create.select(ACCOUNT.EMAIL,
            ACCOUNT.USER_NAME, ACCOUNT.ID, ROLE.ROLE_NAME, ACCOUNT_VERIFICATION.STATUS,
            ACCOUNT.PASSWORD, ACCOUNT.FIRS_NAME, ACCOUNT.LAST_NAME, hasStore)
        .from(ACCOUNT)
        .leftJoin(ACCOUNT_VERIFICATION)
        .on(ACCOUNT.ID.eq(ACCOUNT_VERIFICATION.ACCOUNT_ID))
        .leftJoin(ROLE)
        .on(ACCOUNT.ROLE_ID.eq(ROLE.ID))
        .where(ACCOUNT.ID.eq(id))
        .fetchOne();
    if (result == null) {
      return Optional.empty();
    }

    final Account account = new Account();
    account.setVerified(
        result.get(ACCOUNT_VERIFICATION.STATUS) == AccountVerificationStatus.VERIFIED);
    account.setRole(RoleType.valueOf(result.get(ROLE.ROLE_NAME)));
    account.setId(result.get(ACCOUNT.ID));
    account.setUserName(result.get(ACCOUNT.USER_NAME));
    account.setEmail(result.get(ACCOUNT.EMAIL));
    account.setPassword(result.get(ACCOUNT.PASSWORD));
    account.setFirstName(result.get(ACCOUNT.FIRS_NAME));
    account.setLastName(result.get(ACCOUNT.LAST_NAME));
    account.setHasStore(result.get(hasStore));

    return Optional.of(account);
  }

  @Override
  public boolean existByEmailOrUserName(String email, String userName) {
    return create.fetchExists(
        create.selectOne()
            .from(ACCOUNT)
            .where(ACCOUNT.EMAIL.eq(email).or(ACCOUNT.USER_NAME.eq(userName)))
    );
  }

  @Override
  public Account saveNewSeller(@NotNull Account accountEntity) {
    return Objects.requireNonNull(create.insertInto(ACCOUNT)
        .set(ACCOUNT.EMAIL, accountEntity.getEmail())
        .set(ACCOUNT.USER_NAME, accountEntity.getUserName())
        .set(ACCOUNT.FIRS_NAME, accountEntity.getFirstName())
        .set(ACCOUNT.LAST_NAME, accountEntity.getLastName())
        .set(ACCOUNT.PASSWORD, accountEntity.getPassword())
        .set(ACCOUNT.ROLE_ID, selectRoleIdByType(accountEntity.getRole()))
        .returningResult(
            ACCOUNT.ID, ACCOUNT.EMAIL, ACCOUNT.USER_NAME
        ).fetchOne()).map(record -> {
      var account = new Account();
      account.setId(record.get(ACCOUNT.ID));
      account.setEmail(record.get(ACCOUNT.EMAIL));
      account.setUserName(record.get(ACCOUNT.USER_NAME));
      return account;
    });
  }

  @Override
  public Optional<Account> findByEmail(String email) {
    var result = create.select(ACCOUNT.EMAIL, ACCOUNT.USER_NAME,
            ACCOUNT.ID, ROLE.ROLE_NAME, ACCOUNT_VERIFICATION.STATUS, ACCOUNT.PASSWORD)
        .from(ACCOUNT)
        .leftJoin(ACCOUNT_VERIFICATION)
        .on(ACCOUNT.ID.eq(ACCOUNT_VERIFICATION.ACCOUNT_ID))
        .leftJoin(ROLE)
        .on(ACCOUNT.ROLE_ID.eq(ROLE.ID))
        .where(ACCOUNT.EMAIL.eq(email))
        .fetchOne();
    if (result == null) {
      return Optional.empty();
    }

    final Account account = new Account();
    account.setVerified(
        result.get(ACCOUNT_VERIFICATION.STATUS) == AccountVerificationStatus.VERIFIED);
    account.setRole(RoleType.valueOf(result.get(ROLE.ROLE_NAME)));
    account.setId(result.get(ACCOUNT.ID));
    account.setUserName(result.get(ACCOUNT.USER_NAME));
    account.setEmail(result.get(ACCOUNT.EMAIL));
    account.setPassword(result.get(ACCOUNT.PASSWORD));
    return Optional.of(account);
  }

  @Override
  public void updatePasswordByAccountId(Long accountId, String password) {
    create.update(ACCOUNT)
        .set(ACCOUNT.PASSWORD, password)
        .where(ACCOUNT.ID.eq(accountId))
        .execute();
  }

  @Override
  public List<AccountStoreDto> getAllAccounts() {
    var hasStore = DSL.field(STORE.ID.isNotNull());
    var result = create.select(ACCOUNT.ID, ACCOUNT.USER_NAME, ACCOUNT.ID, ROLE.ROLE_NAME,
            ACCOUNT.EMAIL, ACCOUNT.FIRS_NAME, ACCOUNT.LAST_NAME, ROLE.ROLE_NAME, hasStore)
        .from(ACCOUNT)
        .leftJoin(STORE).on(STORE.ACCOUNT_ID.eq(ACCOUNT.ID))
        .join(ROLE).on(ROLE.ID.eq(ACCOUNT.ROLE_ID))
        .fetch();
    if (result.isEmpty()) {
      return Collections.emptyList();
    }
    return result.map(record -> new AccountStoreDto(
        record.get(ACCOUNT.ID), record.get(ACCOUNT.FIRS_NAME), record.get(ACCOUNT.USER_NAME),
        record.get(ACCOUNT.LAST_NAME), record.get(ACCOUNT.EMAIL),
        RoleType.valueOf(record.get(ROLE.ROLE_NAME)), record.get(hasStore)
    ));
  }

  private Long selectRoleIdByType(RoleType roleType) {
    return create.select(ROLE.ID)
        .from(ROLE)
        .where(ROLE.ROLE_NAME.eq(roleType.name()))
        .fetchOne(ROLE.ID);
  }
}
