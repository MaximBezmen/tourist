package com.adstore.repository;

import com.adstore.dto.store.SocialNetworkDto;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;

/**
 * @author Maxim Bezmen. 24.07.2022
 */
public interface SocialNetworkRepository {

  Long saveSocialNetWork(
      @NotNull DSLContext create, @NotNull SocialNetworkDto socialNetwork, long storeId
  );

  void updateSocialNetWork(
      @NotNull DSLContext create, @NotNull SocialNetworkDto socialNetwork, long storeId
  );
}
