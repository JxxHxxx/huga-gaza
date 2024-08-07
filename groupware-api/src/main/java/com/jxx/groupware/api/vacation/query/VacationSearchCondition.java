package com.jxx.groupware.api.vacation.query;

import com.jxx.groupware.core.vacation.domain.entity.VacationStatus;
import com.jxx.groupware.core.vacation.domain.entity.VacationType;

import java.time.LocalDateTime;
import java.util.List;

public record VacationSearchCondition(

        String companyId,
        String departmentId,
        String requesterId,
        List<VacationStatus> vacationStatus,
        VacationType vacationType,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
        )
{
}
