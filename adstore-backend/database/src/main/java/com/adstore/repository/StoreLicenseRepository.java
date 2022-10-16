package com.adstore.repository;

import com.adstore.dto.store.StoreLicenseDto;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;

/**
 * @author Maxim Bezmen. 25.07.2022
 */
public interface StoreLicenseRepository {

  Long saveLicense(@NotNull DSLContext dsl, @NotNull StoreLicenseDto storeLicenseDto, long storeId);

  void updateLicense(
      @NotNull DSLContext create, @NotNull StoreLicenseDto storeLicenseDto, long storeId
  );
}
