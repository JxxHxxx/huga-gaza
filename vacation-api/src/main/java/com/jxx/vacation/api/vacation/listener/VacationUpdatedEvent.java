package com.jxx.vacation.api.vacation.listener;

import com.jxx.vacation.core.vacation.domain.entity.Vacation;

public record VacationUpdatedEvent(
        String delegatorId,
        String delegatorName,
        String reason,
        Vacation vacation,
        Long contentPk
) {
}