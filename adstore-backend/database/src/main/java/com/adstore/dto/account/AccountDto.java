package com.adstore.dto.account;

import com.adstore.types.RoleType;
import lombok.Data;

@Data
public class AccountDto {

  private Long id;
  private String firstName;
  private String userName;
  private String lastName;
  private String password;
  private String matchingPassword;
  private String email;
  private RoleType role;
}
