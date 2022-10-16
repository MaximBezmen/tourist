package com.adstore.repository;

import com.adstore.dto.store.WorkTimeDto;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;

/**
 * @author Maxim Bezmen. 24.07.2022
 */
public interface WorkTimeRepository {

  Long saveWorkTime(@NotNull DSLContext configuration, @NotNull WorkTimeDto workTime, long storeId);

  void updateWorkTime(@NotNull DSLContext create, @NotNull WorkTimeDto wt, long storeId);
}
