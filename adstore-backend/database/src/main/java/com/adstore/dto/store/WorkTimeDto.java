package com.adstore.dto.store;

import com.adstore.jooq.enums.WeekDay;
import java.time.OffsetDateTime;
import lombok.Data;

/**
 * @author Maxim Bezmen. 24.07.2022
 */
@Data
public class WorkTimeDto {

  private WeekDay dayOfWeek;
  private OffsetDateTime open;
  private OffsetDateTime close;
}
