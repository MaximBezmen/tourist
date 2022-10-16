package com.adstore.entity;

import java.time.Instant;
import lombok.Data;

@Data
public class VerificationToken {

  private static final int EXPIRATION = 60 * 24;
  private String token;
  private Long accountId;
  private Instant expiryAt;
  private Boolean verified;
}
