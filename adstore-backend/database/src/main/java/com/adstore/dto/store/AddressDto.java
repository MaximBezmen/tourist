package com.adstore.dto.store;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {

  private Long id;
  @NotNull
  private String city;
  @NotNull
  private String street;
  @NotNull
  private String house;
  private String block;
}
