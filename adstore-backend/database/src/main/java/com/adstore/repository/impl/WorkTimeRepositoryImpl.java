package com.adstore.repository.impl;

import static com.adstore.jooq.tables.WorkTime.WORK_TIME;

import com.adstore.dto.store.WorkTimeDto;
import com.adstore.repository.WorkTimeRepository;
import javax.validation.constraints.NotNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

/**
 * @author Maxim Bezmen. 24.07.2022
 */
@Repository
public class WorkTimeRepositoryImpl implements WorkTimeRepository {

  @Override
  public Long saveWorkTime(@NotNull DSLContext create, @NotNull WorkTimeDto workTime,
      long storeId) {
    return create.insertInto(WORK_TIME)
        .set(WORK_TIME.WEEK_DAY, workTime.getDayOfWeek())
        .set(WORK_TIME.OPEN, workTime.getOpen())
        .set(WORK_TIME.CLOSE, workTime.getClose())
        .set(WORK_TIME.STORE_ID, storeId)
        .returningResult(WORK_TIME.ID)
        .fetchOne().value1();
  }

  @Override
  public void updateWorkTime(@NotNull DSLContext create, @NotNull WorkTimeDto workTime, long storeId) {
    create.update(WORK_TIME)
        .set(WORK_TIME.OPEN, workTime.getOpen())
        .set(WORK_TIME.CLOSE, workTime.getClose())
        .where(WORK_TIME.STORE_ID.eq(storeId).and(WORK_TIME.WEEK_DAY.eq(workTime.getDayOfWeek())))
        .execute();
  }
}
