package com.adstore.repository.impl;

import static com.adstore.jooq.tables.SocialNetwork.SOCIAL_NETWORK;

import com.adstore.dto.store.SocialNetworkDto;
import com.adstore.repository.SocialNetworkRepository;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Bezmen. 24.07.2022
 */
@Repository
public class SocialNetworkRepositoryImpl implements SocialNetworkRepository {

  @Override
  public Long saveSocialNetWork(
      @NotNull final DSLContext create, @NotNull final SocialNetworkDto socialNetwork,
      final long storeId
  ) {
    return create.insertInto(SOCIAL_NETWORK)
        .set(SOCIAL_NETWORK.FACEBOOK, socialNetwork.getFacebook())
        .set(SOCIAL_NETWORK.VK, socialNetwork.getVk())
        .set(SOCIAL_NETWORK.INSTAGRAM, socialNetwork.getInstagram())
        .set(SOCIAL_NETWORK.STORE_ID, storeId)
        .returningResult(SOCIAL_NETWORK.ID)
        .fetchOne().value1();
  }

  @Override
  public void updateSocialNetWork(
      @NotNull DSLContext create, @NotNull SocialNetworkDto socialNetwork, long storeId
  ) {
    create.update(SOCIAL_NETWORK)
        .set(SOCIAL_NETWORK.FACEBOOK, socialNetwork.getFacebook())
        .set(SOCIAL_NETWORK.VK, socialNetwork.getVk())
        .set(SOCIAL_NETWORK.INSTAGRAM, socialNetwork.getInstagram())
        .where(SOCIAL_NETWORK.STORE_ID.eq(storeId))
        .execute();
  }
}
