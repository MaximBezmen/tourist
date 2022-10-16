package com.adstore.repository.impl;

import static com.adstore.jooq.tables.StoreAddress.STORE_ADDRESS;

import com.adstore.dto.store.AddressDto;
import com.adstore.repository.AddressRepository;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Bezmen. 24.07.2022
 */
@Repository
public class AddressRepositoryImpl implements AddressRepository {

  @Override
  public Long saveAddress(
      @NotNull final DSLContext create, @NotNull final AddressDto address, final long storeId
  ) {
    return create.insertInto(STORE_ADDRESS)
        .set(STORE_ADDRESS.CITY, address.getCity())
        .set(STORE_ADDRESS.STREET, address.getStreet())
        .set(STORE_ADDRESS.HOUSE, address.getHouse())
        .set(STORE_ADDRESS.BLOCK, address.getBlock())
        .set(STORE_ADDRESS.STORE_ID, storeId)
        .returningResult(STORE_ADDRESS.ID)
        .fetchOne().value1();
  }

  @Override
  public void updateAddress(
      @NotNull DSLContext create, @NotNull AddressDto address, @NotNull long storeId
  ) {
    create.update(STORE_ADDRESS)
        .set(STORE_ADDRESS.CITY, address.getCity())
        .set(STORE_ADDRESS.STREET, address.getStreet())
        .set(STORE_ADDRESS.HOUSE, address.getHouse())
        .set(STORE_ADDRESS.BLOCK, address.getBlock())
        .where(STORE_ADDRESS.STORE_ID.eq(storeId))
        .execute();
  }
}
