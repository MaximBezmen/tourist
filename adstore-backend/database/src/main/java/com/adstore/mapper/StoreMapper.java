package com.adstore.mapper;

import static com.adstore.jooq.tables.SocialNetwork.SOCIAL_NETWORK;
import static com.adstore.jooq.tables.Store.STORE;
import static com.adstore.jooq.tables.StoreAddress.STORE_ADDRESS;
import static com.adstore.jooq.tables.StoreLicense.STORE_LICENSE;
import static com.adstore.jooq.tables.WorkTime.WORK_TIME;

import com.adstore.dto.store.AddressDto;
import com.adstore.dto.store.RecommendStore;
import com.adstore.dto.store.SocialNetworkDto;
import com.adstore.dto.store.StoreDto;
import com.adstore.dto.store.StoreLicenseDto;
import com.adstore.dto.store.WorkTimeDto;
import com.adstore.jooq.enums.WeekDay;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import org.jooq.Record14;
import org.jooq.Record18;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.stereotype.Component;

/**
 * @author Maxim Bezmen. 25.08.2022
 */
@Component
public class StoreMapper {

  public StoreDto toStoreDto(
      Result<Record14<Long, String, String, String, String[], String, Long, WeekDay,
          OffsetDateTime, OffsetDateTime, String, String, String, String>> value
  ) {
    AddressDto addressDto = new AddressDto();
    addressDto.setCity(value.getValue(0, STORE_ADDRESS.CITY));
    addressDto.setStreet(value.getValue(0, STORE_ADDRESS.STREET));
    addressDto.setHouse(value.getValue(0, STORE_ADDRESS.HOUSE));
    addressDto.setBlock(value.getValue(0, STORE_ADDRESS.BLOCK));
    StoreDto storeDto = new StoreDto();
    storeDto.setName(value.getValue(0, STORE.NAME));
    storeDto.setAbout(value.getValue(0, STORE.ABOUT));
    storeDto.setUrl(value.getValue(0, STORE.URL));
    storeDto.setPhones(Arrays.stream(value.getValue(0, STORE.PHONES)).toList());
    storeDto.setStoreLogo(value.getValue(0, STORE.STORE_LOGO));
    storeDto.setAddress(addressDto);

    List<WorkTimeDto> workTimes = value.map(record -> {
      WorkTimeDto workTimeDto = new WorkTimeDto();
      workTimeDto.setDayOfWeek(record.getValue(WORK_TIME.WEEK_DAY));
      workTimeDto.setOpen(record.getValue(WORK_TIME.OPEN));
      workTimeDto.setClose(record.getValue(WORK_TIME.CLOSE));
      return workTimeDto;
    }).stream().toList();
    storeDto.setWorkTimes(workTimes);
    return storeDto;
  }

  public StoreDto toFullStoreDto(
      Result<Record18<Long, String, String, String, String[], String, Long, WeekDay, OffsetDateTime,
          OffsetDateTime, String, String, String, String, String, String, String, String>> value
  ) {
    AddressDto addressDto = new AddressDto();
    addressDto.setCity(value.getValue(0, STORE_ADDRESS.CITY));
    addressDto.setStreet(value.getValue(0, STORE_ADDRESS.STREET));
    addressDto.setHouse(value.getValue(0, STORE_ADDRESS.HOUSE));
    addressDto.setBlock(value.getValue(0, STORE_ADDRESS.BLOCK));
    StoreDto storeDto = new StoreDto();
    storeDto.setName(value.getValue(0, STORE.NAME));
    storeDto.setId(value.getValue(0, STORE.ID));
    storeDto.setAbout(value.getValue(0, STORE.ABOUT));
    storeDto.setUrl(value.getValue(0, STORE.URL));
    storeDto.setPhones(Arrays.stream(value.getValue(0, STORE.PHONES)).toList());
    storeDto.setStoreLogo(value.getValue(0, STORE.STORE_LOGO));
    storeDto.setAddress(addressDto);

    List<WorkTimeDto> workTimes = value.map(record -> {
      WorkTimeDto workTimeDto = new WorkTimeDto();
      workTimeDto.setDayOfWeek(record.getValue(WORK_TIME.WEEK_DAY));
      workTimeDto.setOpen(record.getValue(WORK_TIME.OPEN));
      workTimeDto.setClose(record.getValue(WORK_TIME.CLOSE));
      return workTimeDto;
    }).stream().toList();
    SocialNetworkDto socialNetworkDto = new SocialNetworkDto();
    socialNetworkDto.setVk(value.getValue(0, SOCIAL_NETWORK.VK));
    socialNetworkDto.setFacebook(value.getValue(0, SOCIAL_NETWORK.FACEBOOK));
    socialNetworkDto.setInstagram(value.getValue(0, SOCIAL_NETWORK.INSTAGRAM));

    StoreLicenseDto storeLicenseDto = new StoreLicenseDto();
    storeLicenseDto.setUnp(value.getValue(0, STORE_LICENSE.UNP));

    storeDto.setStoreLicense(storeLicenseDto);
    storeDto.setSocialNetwork(socialNetworkDto);
    storeDto.setWorkTimes(workTimes);
    return storeDto;
  }

  public RecommendStore toRecommendStore(Record3<String, String, String> record) {
    return new RecommendStore
        (record.getValue(STORE.URL), record.getValue(STORE.STORE_LOGO), record.getValue(STORE.NAME)
        );
  }
}
