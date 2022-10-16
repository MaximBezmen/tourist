package com.adstore.dto.store;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StoreDto {

  private Long id;
  @NotNull
  private String name;
  private AddressDto address;
  private SocialNetworkDto socialNetwork;
  private StoreLicenseDto storeLicense;
  private String about;
  @NotNull
  private String url;
  private List<WorkTimeDto> workTimes;
  private List<String> phones;
  private String headerImage;
  private String storeLogo;
  private String footerImage;
  private String requestInfo;
}
