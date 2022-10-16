package com.adstore.repository;

import com.adstore.dto.store.RecommendStore;
import com.adstore.dto.store.StoreDto;
import com.adstore.dto.store.filter.StoreFilter;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;

public interface StoreRepository {

  Long saveStore(@NotNull DSLContext create, long sellerId, @NotNull StoreDto store);

  StoreDto getStoreBySeller(@NotNull Long accountId);

  Long getStoreIdBySellerId(long sellerId);

  void updateStoreById(
      @NotNull DSLContext create, long storeId, long sellerId, @NotNull StoreDto dto
  );

  List<StoreDto> getAllStores(StoreFilter filter);

  List<RecommendStore> getRecommendStore();

  StoreDto getStoreByUrl(String url);
}
