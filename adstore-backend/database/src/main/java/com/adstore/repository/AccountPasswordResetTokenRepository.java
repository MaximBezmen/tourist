package com.adstore.repository;

import com.adstore.entity.VerificationToken;
import javax.validation.constraints.NotNull;

/**
 * @author Maxim Bezmen. 14.07.2022
 */
public interface AccountPasswordResetTokenRepository {

  void saveResetTokenForUser(Long accountId, String token);

  VerificationToken findByToken(@NotNull String token);

  void deleteVerificationTokenByAccountIdAndToken(Long id, String token);
}
