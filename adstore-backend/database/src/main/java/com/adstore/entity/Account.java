package com.adstore.entity;

import com.adstore.types.RoleType;
import lombok.Data;

/**
 * @author Maxim Bezmen. 12.07.2022
 */
@Data
public class Account {

  private Long id;
  private String email;
  private String firstName;
  private String lastName;
  private String password;
  private String userName;
  private boolean isVerified = false;
  private RoleType role;
  private boolean hasStore;
}
