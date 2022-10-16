package com.adstore.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Maxim Bezmen. 14.08.2022
 */
@Data
public class LoginDto {

  @NotNull
  @NotEmpty
  private String login;

  @NotNull
  @NotEmpty
  private String password;
}
