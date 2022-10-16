package com.adstore.mapper;

import static com.adstore.jooq.tables.StoreAd.STORE_AD;

import com.adstore.dto.store.StoreAdDto;
import java.time.OffsetDateTime;
import java.util.Arrays;
import org.jooq.Record9;
import org.springframework.stereotype.Component;

/**
 * @author Maxim Bezmen. 24.08.2022
 */
@Component
public class StoreAdMapper {

  public StoreAdDto toStoreAdDto(
      Record9<Long, Long, String, String, String[], Double, Boolean, OffsetDateTime, Long> record) {
    StoreAdDto dto = new StoreAdDto();
    dto.setId(record.getValue(STORE_AD.ID));
    dto.setStoreId(record.getValue(STORE_AD.STORE_ID));
    dto.setTitle(record.getValue(STORE_AD.TITLE));
    dto.setDescription(record.getValue(STORE_AD.DESCRIPTION));
    dto.setImages(Arrays.stream(record.getValue(STORE_AD.IMAGES)).toList());
    dto.setPrice(record.getValue(STORE_AD.PRICE));
    dto.setIsActive(record.getValue(STORE_AD.IS_ACTIVE));
    dto.setCreateAt(record.getValue(STORE_AD.CREATE_AT));
    dto.setView(record.getValue(STORE_AD.VIEW));
    return dto;
  }

}
