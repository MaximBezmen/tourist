package com.adstore.dto.store;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;

@Data
public class StoreAdDto {

  private Long id;
  private Long storeId;
  private String title;
  private String description;
  private List<String> images;
  private Double price;
  private OffsetDateTime createAt;
  private Boolean isActive;
  private String requestInfo;
  private Long view;
}
