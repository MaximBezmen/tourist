package com.adstore.repository.impl;

import static com.adstore.jooq.Tables.ACCOUNT;
import static com.adstore.jooq.Tables.STORE;
import static com.adstore.jooq.tables.StoreAd.STORE_AD;
import static org.jooq.impl.DSL.upper;

import com.adstore.dto.AdActionDto;
import com.adstore.dto.store.StoreAdDto;
import com.adstore.dto.ad.filter.AdFilter;
import com.adstore.mapper.StoreAdMapper;
import com.adstore.repository.StoreAdRepository;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import javax.validation.constraints.NotNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Bezmen. 30.07.2022
 */
@Repository
public class StoreAdRepositoryImpl implements StoreAdRepository {

  private final DSLContext create;
  private final StoreAdMapper storeAdMapper;

  public StoreAdRepositoryImpl(final DSLContext create, final StoreAdMapper storeAdMapper) {
    this.create = create;
    this.storeAdMapper = storeAdMapper;
  }

  @Override
  public void createStoreAd(@NotNull StoreAdDto storeAdDto, long sellerId) {
    create.insertInto(STORE_AD)
        .set(STORE_AD.STORE_ID, storeAdDto.getStoreId())
        .set(STORE_AD.TITLE, storeAdDto.getTitle())
        .set(STORE_AD.DESCRIPTION, storeAdDto.getDescription())
        .set(STORE_AD.IMAGES, storeAdDto.getImages().toArray(new String[0]))
        .set(STORE_AD.PRICE, storeAdDto.getPrice())
        .set(STORE_AD.CREATE_AT, OffsetDateTime.now(ZoneOffset.UTC))
        .set(STORE_AD.REQUEST_INFO, storeAdDto.getRequestInfo())
        .execute();
  }

  @Override
  public List<StoreAdDto> getStoreAdsOfSeller(Long sellerId) {
    return create.select(STORE_AD.ID, STORE_AD.STORE_ID, STORE_AD.TITLE, STORE_AD.DESCRIPTION,
            STORE_AD.IMAGES, STORE_AD.PRICE, STORE_AD.IS_ACTIVE, STORE_AD.CREATE_AT, STORE_AD.VIEW)
        .from(STORE_AD)
        .leftJoin(STORE).on(STORE.ID.eq(STORE_AD.STORE_ID).and(STORE.ACCOUNT_ID.eq(sellerId)))
        .where(STORE_AD.STORE_ID.eq(STORE.ID).and(STORE_AD.IS_DELETED.isFalse()))
        .fetch().map(storeAdMapper::toStoreAdDto);
  }

  @Override
  public List<StoreAdDto> getStoreAds(@NotNull String url, AdFilter filter) {
    Condition condition = STORE_AD.IS_ACTIVE.isTrue().and(STORE_AD.IS_DELETED.isFalse());
    if (filter.title() != null) {
      condition = condition.and(
          upper(STORE_AD.TITLE).like("%" + filter.title().toUpperCase(Locale.ROOT) + "%"));
    }
    if (filter.description() != null) {
      condition = condition.and(upper(STORE_AD.DESCRIPTION)
          .like("%" + filter.description().toUpperCase(Locale.ROOT) + "%"));
    }
    if (filter.priceFrom() != null) {
      condition = condition.and(STORE_AD.PRICE.greaterOrEqual(filter.priceFrom()));
    }
    if (filter.priceTo() != null) {
      condition = condition.and(STORE_AD.PRICE.lessOrEqual(filter.priceTo()));
    }
    return getStoreAds(url, condition);
  }

  @Override
  public void changeStatsAdBy(Long id, Long sellerId, AdActionDto action) {
    create.update(STORE_AD)
        .set(STORE_AD.IS_ACTIVE, action.getNewStatus())
        .where(STORE_AD.ID.eq(id).and(
            STORE_AD.STORE_ID.eq(
                DSL.select(STORE.ID)
                    .from(STORE)
                    .join(ACCOUNT).on(ACCOUNT.ID.eq(STORE.ACCOUNT_ID))
                    .where(ACCOUNT.ID.eq(sellerId))
            )
        ))
        .execute();
  }

  @Override
  public void softDeleteAdById(Long adId, Long sellerId) {
    create.update(STORE_AD)
        .set(STORE_AD.IS_DELETED, true)
        .set(STORE_AD.DELETE_AT, OffsetDateTime.now(ZoneOffset.UTC))
        .where(STORE_AD.ID.eq(adId)
            .and(STORE_AD.IS_DELETED.isFalse())
            .and(STORE_AD.STORE_ID.eq(DSL.select(STORE.ID)
                .from(STORE)
                .where(STORE.ACCOUNT_ID.eq(sellerId)))))
        .execute();
  }

  @Override
  public void updateAd(Long id, StoreAdDto storeAdDto, Long sellerId) {
    create.update(STORE_AD)
        .set(STORE_AD.TITLE, storeAdDto.getTitle())
        .set(STORE_AD.DESCRIPTION, storeAdDto.getDescription())
        .set(STORE_AD.IMAGES, storeAdDto.getImages().toArray(new String[0]))
        .set(STORE_AD.PRICE, storeAdDto.getPrice())
        .where(STORE_AD.ID.eq(id)
            .and(STORE_AD.STORE_ID.eq(DSL.select(STORE.ID)
                .from(STORE)
                .where(STORE.ACCOUNT_ID.eq(sellerId)))))
        .execute();
  }

  @Override
  public void addViewAdById(long id) {
    create.update(STORE_AD)
        .set(STORE_AD.VIEW, STORE_AD.VIEW.plus(1))
        .where(STORE_AD.ID.eq(id))
        .execute();
  }

  private List<StoreAdDto> getStoreAds(String url, Condition condition) {
    return create.select(STORE_AD.ID, STORE_AD.STORE_ID, STORE_AD.TITLE, STORE_AD.DESCRIPTION,
            STORE_AD.IMAGES, STORE_AD.PRICE, STORE_AD.IS_ACTIVE, STORE_AD.CREATE_AT,
            STORE_AD.VIEW)
        .from(STORE_AD)
        .join(STORE).on(STORE.URL.eq(url).and(STORE.ID.eq(STORE_AD.STORE_ID)))
        .where(condition)
        .fetch().map(storeAdMapper::toStoreAdDto);
  }
}
