package com.jxx.groupware.core.vacation.domain.entity;

import lombok.Getter;

import java.util.List;

@Getter
public enum VacationStatus {

    CREATE("연차 신청서 작성 완료한 상태"), // 최초
    REQUEST("상신 요청 완료"), // 휴가 서버에서 결정 - REST API
    REJECT("결재 반려"), // 걸재 서버에서 결정
    APPROVED("결재 승인"), // 결재 서버에서 결정
    NON_REQUIRED("결재 승인 필요X"),
    CANCELED("연차 취소"), // 휴가 서버에서 결정 - REST API
    ONGOING("휴가 사용 중"), // 배치에서 처리
    COMPLETED("연차 소진 완료"), // 배치 동작
    FAIL("신청 실패"),
    ERROR("시스템 상 처리 실패"); // Deprecated

    private final String description;

    public static final List<VacationStatus> CANCEL_POSSIBLE_GROUP = List.of(CREATE);
    // 휴가 신청 시, 신청 가능한 일 수 인지 파악해야 할 때, 필요한 그룹
    public static final List<VacationStatus> CONFIRMING_GROUP = List.of(REQUEST, APPROVED);

    // 에러/반려 등이 아닌 상신 및 상신 이후 상태의 휴가
    public static final List<VacationStatus> RAISE_APPROVE_ONGOING_VS = List.of(REQUEST, APPROVED, ONGOING);

    VacationStatus(String description) {
        this.description = description;
    }

    public static boolean isOngoing(String vacationStatus) {
        return ONGOING.equals(VacationStatus.valueOf(vacationStatus));
    }

    public static boolean isOngoing(VacationStatus vacationStatus) {
        return ONGOING.equals(vacationStatus);
    }

    public static boolean isApproved(String vacationStatus) {
        return APPROVED.equals(VacationStatus.valueOf(vacationStatus));
    }

    public static boolean isApproved(VacationStatus vacationStatus) {
        return APPROVED.equals(vacationStatus);
    }
}
