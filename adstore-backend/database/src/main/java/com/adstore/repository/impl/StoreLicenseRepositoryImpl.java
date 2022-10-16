package com.adstore.repository.impl;

import static com.adstore.jooq.tables.StoreLicense.STORE_LICENSE;

import com.adstore.dto.store.StoreLicenseDto;
import com.adstore.repository.StoreLicenseRepository;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Bezmen. 25.07.2022
 */
@Repository
public class StoreLicenseRepositoryImpl implements StoreLicenseRepository {

  @Override
  public Long saveLicense(
      @NotNull final DSLContext create, @NotNull final StoreLicenseDto dto, final long storeId
  ) {
    return create.insertInto(STORE_LICENSE)
        .set(STORE_LICENSE.UNP, dto.getUnp())
        .set(STORE_LICENSE.STORE_ID, storeId)
        .returningResult(STORE_LICENSE.ID)
        .fetchOne().value1();
  }

  @Override
  public void updateLicense(@NotNull DSLContext create, @NotNull StoreLicenseDto dto, long storeId) {
    create.update(STORE_LICENSE)
        .set(STORE_LICENSE.UNP, dto.getUnp())
        .where(STORE_LICENSE.STORE_ID.eq(storeId))
        .execute();
  }
}
