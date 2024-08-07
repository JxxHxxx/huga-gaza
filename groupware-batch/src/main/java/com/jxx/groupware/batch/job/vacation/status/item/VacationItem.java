package com.jxx.groupware.batch.job.vacation.status.item;


import com.jxx.groupware.core.vacation.domain.entity.VacationStatus;
import com.jxx.groupware.core.vacation.domain.entity.VacationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter // writer - beanMapped 용
@AllArgsConstructor
public class VacationItem {
    private final Long vacationId;
    private final String leaveDeduct;
    private final LocalDateTime createTime;
    private final String requesterId;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final VacationType vacationType;
    private final String companyId;
    private String vacationStatus;

    public void changeVacationStatus(VacationStatus vacationStatus) {
        this.vacationStatus = String.valueOf(vacationStatus);
    }
}

