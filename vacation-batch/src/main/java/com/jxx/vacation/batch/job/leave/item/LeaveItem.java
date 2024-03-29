package com.jxx.vacation.batch.job.leave.item;

import com.jxx.vacation.core.vacation.domain.entity.VacationDuration;
import com.jxx.vacation.core.vacation.domain.entity.VacationType;
import com.jxx.vacation.core.vacation.domain.service.VacationCalculator;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class LeaveItem {

    private final String memberPk;
    private final boolean memberActive;
    private final Float totalLeave;
    private final Float remainingLeave;
    private final String name;
    private final String memberId;
    private final Integer experienceYears;
    private final LocalDate enteredDate;
    private final Long vacationId;
    private final boolean deducted;
    private String vacationStatus;
    private final String vacationType;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final String companyId;
    private final String departmentId;
    private final boolean orgActive;
    private Float deductedAmount;

    public LeaveItem(String memberPk, boolean memberActive,Float totalLeave,  Float remainingLeave, String name, String memberId, Integer experienceYears, LocalDate enteredDate,
                     Long vacationId, boolean deducted, String vacationStatus, String vacationType, LocalDateTime startDateTime, LocalDateTime endDateTime, String companyId, String departmentId, boolean orgActive) {
        this.memberPk = memberPk;
        this.memberActive = memberActive;
        this.totalLeave = totalLeave;
        this.remainingLeave = remainingLeave;
        this.name = name;
        this.memberId = memberId;
        this.experienceYears = experienceYears;
        this.enteredDate = enteredDate;
        this.vacationId = vacationId;
        this.deducted = deducted;
        this.vacationStatus = vacationStatus;
        this.vacationType = vacationType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.companyId = companyId;
        this.departmentId = departmentId;
        this.orgActive = orgActive;
        this.deductedAmount = 0f;
    }

    public boolean checkMemberOrgActive() {
        return memberActive && orgActive ? true : false;
    }

    public void calculateDeductAmount() {
        VacationDuration vacationDuration = new VacationDuration(VacationType.valueOf(vacationType), startDateTime, endDateTime);
        deductedAmount = VacationCalculator.getVacationDuration(vacationDuration, deducted);
    }

    public void updateVacationStatusToError() {
        this.vacationStatus = "ERROR";
    }

    public void updateVacationStatusToCompleted() {
        this.vacationStatus = "COMPLETED";
    }
}

