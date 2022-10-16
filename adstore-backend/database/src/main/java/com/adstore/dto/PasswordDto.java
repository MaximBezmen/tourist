package com.adstore.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordDto {

  @NotNull
  @NotEmpty
  private String newPassword;
  @NotNull
  @NotEmpty
  private String oldPassword;
  @NotNull
  @NotEmpty
  private String matchingNewPassword;
  @NotNull
  @NotEmpty
  private String token;
}
