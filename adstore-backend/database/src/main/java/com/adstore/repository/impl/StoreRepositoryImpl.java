package com.adstore.repository.impl;

import static com.adstore.jooq.tables.SocialNetwork.SOCIAL_NETWORK;
import static com.adstore.jooq.tables.Store.STORE;
import static com.adstore.jooq.tables.StoreAddress.STORE_ADDRESS;
import static com.adstore.jooq.tables.StoreLicense.STORE_LICENSE;
import static com.adstore.jooq.tables.WorkTime.WORK_TIME;

import com.adstore.dto.store.RecommendStore;
import com.adstore.dto.store.StoreDto;
import com.adstore.dto.store.filter.StoreFilter;
import com.adstore.mapper.StoreMapper;
import com.adstore.repository.StoreRepository;
import java.util.List;
import java.util.Locale;
import javax.validation.constraints.NotNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Bezmen. 24.07.2022
 */
@Repository
public class StoreRepositoryImpl implements StoreRepository {

  private final DSLContext create;
  private final StoreMapper storeMapper;

  public StoreRepositoryImpl(final DSLContext create, final StoreMapper storeMapper) {
    this.create = create;
    this.storeMapper = storeMapper;
  }

  @Override
  public Long saveStore(
      @NotNull final DSLContext create, final long sellerId, @NotNull final StoreDto dto
  ) {
    return create.insertInto(STORE)
        .set(STORE.ACCOUNT_ID, sellerId)
        .set(STORE.NAME, dto.getName())
        .set(STORE.ABOUT, dto.getAbout())
        .set(STORE.URL, dto.getUrl())
        .set(STORE.PHONES, dto.getPhones().toArray(new String[0]))
        .set(STORE.HEADER_IMAGE, dto.getHeaderImage())
        .set(STORE.STORE_LOGO, dto.getStoreLogo())
        .set(STORE.FOOTER_IMAGE, dto.getFooterImage())
        .set(STORE.REQUEST_INFO, dto.getRequestInfo())
        .onConflictDoNothing()
        .returningResult(STORE.ID)
        .fetchOne().value1();
  }

  @Override
  public StoreDto getStoreBySeller(@NotNull Long accountId) {
    return create.select(
            STORE.ID, STORE.NAME, STORE.ABOUT, STORE.URL, STORE.PHONES,
            STORE.STORE_LOGO, WORK_TIME.STORE_ID, WORK_TIME.WEEK_DAY, WORK_TIME.OPEN,
            WORK_TIME.CLOSE, STORE_ADDRESS.CITY, STORE_ADDRESS.STREET, STORE_ADDRESS.HOUSE,
            STORE_ADDRESS.BLOCK, SOCIAL_NETWORK.FACEBOOK, SOCIAL_NETWORK.VK, SOCIAL_NETWORK.INSTAGRAM,
            STORE_LICENSE.UNP)
        .from(STORE)
        .leftJoin(WORK_TIME).on(WORK_TIME.STORE_ID.eq(STORE.ID))
        .leftJoin(STORE_ADDRESS).on(STORE_ADDRESS.STORE_ID.eq(STORE.ID))
        .leftJoin(SOCIAL_NETWORK).on(SOCIAL_NETWORK.STORE_ID.eq(STORE.ID))
        .leftJoin(STORE_LICENSE).on(STORE_LICENSE.STORE_ID.eq(STORE.ID))
        .where(STORE.ACCOUNT_ID.eq(accountId))
        .fetchGroups(STORE.ID)
        .values().stream()
        .map(storeMapper::toFullStoreDto)
        .findFirst()
        .orElseThrow();
  }

  @Override
  public Long getStoreIdBySellerId(long sellerId) {
    return create.select(STORE.ID)
        .from(STORE)
        .where(STORE.ACCOUNT_ID.eq(sellerId))
        .fetchOne().value1();
  }

  @Override
  public void updateStoreById(
      @NotNull DSLContext create, long storeId, long sellerId, @NotNull StoreDto dto
  ) {
    create.update(STORE)
        .set(STORE.ACCOUNT_ID, sellerId)
        .set(STORE.NAME, dto.getName())
        .set(STORE.ABOUT, dto.getAbout())
        .set(STORE.URL, dto.getUrl())
        .set(STORE.PHONES, dto.getPhones().toArray(new String[0]))
        .set(STORE.HEADER_IMAGE, dto.getHeaderImage())
        .set(STORE.STORE_LOGO, dto.getStoreLogo())
        .set(STORE.FOOTER_IMAGE, dto.getFooterImage())
        .where(STORE.ACCOUNT_ID.eq(sellerId).and(STORE.ID.eq(sellerId)))
        .execute();
  }

  @Override
  public List<StoreDto> getAllStores(final StoreFilter filter) {
    Condition condition = DSL.trueCondition();
    if (filter.name() != null) {
      condition = condition.and(
          DSL.upper(STORE.NAME).like("%" + filter.name().toUpperCase(Locale.ROOT) + "%"));
    }

    if (filter.about() != null) {
      condition = condition.and(
          DSL.upper(STORE.ABOUT).like("%" + filter.about().toUpperCase(Locale.ROOT) + "%"));
    }
    return create.select(
            STORE.ID, STORE.NAME, STORE.ABOUT, STORE.URL, STORE.PHONES,
            STORE.STORE_LOGO, WORK_TIME.STORE_ID, WORK_TIME.WEEK_DAY, WORK_TIME.OPEN,
            WORK_TIME.CLOSE, STORE_ADDRESS.CITY, STORE_ADDRESS.STREET, STORE_ADDRESS.HOUSE,
            STORE_ADDRESS.BLOCK)
        .from(STORE)
        .leftJoin(WORK_TIME).on(WORK_TIME.STORE_ID.eq(STORE.ID))
        .leftJoin(STORE_ADDRESS).on(STORE_ADDRESS.STORE_ID.eq(STORE.ID))
        .where(condition)
        .fetchGroups(STORE.ID)
        .values().stream()
        .map(storeMapper::toStoreDto)
        .toList();
  }

  @Override
  public List<RecommendStore> getRecommendStore() {
    return create.select(STORE.STORE_LOGO, STORE.URL, STORE.NAME)
        .from(STORE)
        .where(STORE.RECOMMENDED.isTrue().and(STORE.URL.isNotNull())
            .and(STORE.NAME.isNotNull()))
        .fetch()
        .stream().map(storeMapper::toRecommendStore)
        .toList();
  }

  @Override
  public StoreDto getStoreByUrl(@NotNull String url) {
    return create.select(
            STORE.ID, STORE.NAME, STORE.ABOUT, STORE.URL, STORE.PHONES,
            STORE.STORE_LOGO, WORK_TIME.STORE_ID, WORK_TIME.WEEK_DAY, WORK_TIME.OPEN,
            WORK_TIME.CLOSE, STORE_ADDRESS.CITY, STORE_ADDRESS.STREET, STORE_ADDRESS.HOUSE,
            STORE_ADDRESS.BLOCK, SOCIAL_NETWORK.FACEBOOK, SOCIAL_NETWORK.VK, SOCIAL_NETWORK.INSTAGRAM,
            STORE_LICENSE.UNP)
        .from(STORE)
        .leftJoin(WORK_TIME).on(WORK_TIME.STORE_ID.eq(STORE.ID))
        .leftJoin(STORE_ADDRESS).on(STORE_ADDRESS.STORE_ID.eq(STORE.ID))
        .leftJoin(SOCIAL_NETWORK).on(SOCIAL_NETWORK.STORE_ID.eq(STORE.ID))
        .leftJoin(STORE_LICENSE).on(STORE_LICENSE.STORE_ID.eq(STORE.ID))
        .where(STORE.URL.eq(url))
        .fetchGroups(STORE.ID)
        .values().stream()
        .map(storeMapper::toFullStoreDto)
        .findFirst()
        .orElseThrow();
  }
}
