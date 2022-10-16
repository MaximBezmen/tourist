package com.adstore.repository;

import com.adstore.dto.store.AddressDto;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;

/**
 * @author Maxim Bezmen. 24.07.2022
 */
public interface AddressRepository {

  Long saveAddress(@NotNull DSLContext create, @NotNull AddressDto address, long storeId);

  void updateAddress(@NotNull DSLContext create, @NotNull AddressDto address, long storeId);
}
