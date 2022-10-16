package com.adstore.repository;

import com.adstore.dto.AdActionDto;
import com.adstore.dto.store.StoreAdDto;
import com.adstore.dto.ad.filter.AdFilter;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * @author Maxim Bezmen. 30.07.2022
 */
public interface StoreAdRepository {

  void createStoreAd(@NotNull StoreAdDto storeAdDto, long sellerId);

  List<StoreAdDto> getStoreAdsOfSeller(Long storeId);

  List<StoreAdDto> getStoreAds(@NotNull String url, AdFilter filter);

  void changeStatsAdBy(Long id, Long sellerId, AdActionDto action);

  void softDeleteAdById(Long adId, Long sellerId);

  void updateAd(Long id, StoreAdDto storeAdDto, Long sellerId);

  void addViewAdById(long id);
}
