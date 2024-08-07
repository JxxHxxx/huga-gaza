package com.jxx.groupware.core.vacation.domain.entity;


import com.jxx.groupware.core.vacation.domain.exeception.VacationClientException;
import com.jxx.groupware.testUtil.CoreEntityFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class VacationManagerTest {

    // 인스턴스 생성 시, 가지는 특징
    @DisplayName("createVacation 정적 팩터리 메서드 사용 시, 인스턴스의 필드는 아래와 같은 특징을 가진다. " +
            "Vacation:신청자의 ID 값을 가진다.  " +
            "VacationDate:휴가 일수를 나타낸다. " +
            "MemberLeave:메서드 인자로 넣은 MemberLeave가 적용된다.")
    @Test
    void instantiate() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(2l);
        List<VacationDuration> vacationDuration = List.of(new VacationDuration(startDate, endDate, LeaveDeduct.DEDUCT, VacationType.MORE_DAY));

        Organization organization = CoreEntityFactory.defalutOrganization();
        MemberLeave memberLeave = CoreEntityFactory.defaultMemberLeave(organization); // memberId == requesterId

        //when
        VacationManager vacationManager = VacationManager.createVacation(memberLeave, VacationType.MORE_DAY, LeaveDeduct.DEDUCT);

        assertThat(vacationManager.getVacation()).extracting("requesterId").isEqualTo(memberLeave.getMemberId());
        assertThat(vacationManager.getMemberLeave()).isEqualTo(memberLeave);
    }

    @DisplayName("Vacation Status 가 REQUEST, APPROVED, ONGOING 에 포함되는 휴가 날짜를 " +
            "다시 휴가로 신청할 수 없다." +
            "만약 해당 상태를 가지는 휴가 날짜를 휴가로 요청할 경우 VacationClientException 이 발생한다.")
    @EnumSource(names = {"REQUEST", "APPROVED", "ONGOING"})
    @ParameterizedTest
    void validate_vacation_dates_are_duplication_throw_exception_case(VacationStatus vacationStatus) {
        //given - 휴가 신청
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 4, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 8, 0, 0);

        Organization organization = CoreEntityFactory.defalutOrganization();
        MemberLeave memberLeave = CoreEntityFactory.defaultMemberLeave(organization); // memberId == requesterId
        VacationManager vacationManager = VacationManager.createVacation(memberLeave, VacationType.MORE_DAY, LeaveDeduct.DEDUCT);
        Vacation vacation = vacationManager.getVacation();
        vacation.addVacationDuration(new VacationDuration(startDate, endDate, LeaveDeduct.DEDUCT, VacationType.MORE_DAY));

        // 기존에 존재하고 있는 아직 사용 전인 휴가
        LocalDateTime startDate2 = LocalDateTime.of(2024, 3, 5, 0, 0);
        LocalDateTime endDate2 = LocalDateTime.of(2024, 3, 6, 0, 0);

        Vacation existedVacation = new Vacation("T0001", "TJX", LeaveDeduct.DEDUCT, VacationType.MORE_DAY, vacationStatus);
        existedVacation.addVacationDuration(new VacationDuration(startDate2, endDate2, LeaveDeduct.DEDUCT, VacationType.MORE_DAY));

        //WHEN - THEN
        assertThatThrownBy(() -> vacationManager.validateVacationDatesAreDuplicated(List.of(existedVacation)))
                .isInstanceOf(VacationClientException.class);
    }

    @DisplayName("Vacation Status 가 CREATE, REJECT, CANCELED, COMPLETED, FAIL, ERROR 에 포함되는 날짜는" +
            "다시 휴가로 신청할 수 있다. 즉,VacationClientException 이 발생하지 않는다." +
            "엄밀히 말해, COMPLETED 의 경우에는 이미 종료된 휴가를 의미하기 때문에 생성 시점 이전 일이다. ")
    @EnumSource(names = {"CREATE", "REJECT", "CANCELED", "COMPLETED", "FAIL", "ERROR"})
    @ParameterizedTest
    void validate_vacation_dates_are_duplication_not_throw_exception_case(VacationStatus vacationStatus) {
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 4, 0, 0);
        List<VacationDuration> vacationDuration = List.of(new VacationDuration(startDate, endDate, LeaveDeduct.DEDUCT, VacationType.MORE_DAY));

        Organization organization = CoreEntityFactory.defalutOrganization();
        MemberLeave memberLeave = CoreEntityFactory.defaultMemberLeave(organization); // memberId == requesterId
        VacationManager vacationManager = VacationManager.createVacation(memberLeave, VacationType.MORE_DAY, LeaveDeduct.DEDUCT);


        // 휴가 상태가 REQUEST, APPROVED, ONGOING 아닌 경우
        LocalDateTime startDate2 = LocalDateTime.of(2024, 3, 2, 0, 0);
        LocalDateTime endDate2 = LocalDateTime.of(2024, 3, 3, 0, 0);
        List<VacationDuration> vacationDuration2 = List.of(new VacationDuration(startDate2, endDate2, LeaveDeduct.DEDUCT, VacationType.MORE_DAY));


        //WHEN - THEN
        Vacation existedVacation = new Vacation("T0001", "TJX", LeaveDeduct.DEDUCT, VacationType.MORE_DAY, vacationStatus);

        assertThatCode(() -> vacationManager.validateVacationDatesAreDuplicated(List.of(existedVacation)))
                .doesNotThrowAnyException();
    }

    @DisplayName("잔여 연차일 - (현재 결재 진행 중인 휴가 + 신청한 휴가일 수) 가 0 보다 작은 음수일 경우 " +
            "검증에 실패하고 VacationClientException 예외가 발생한다.")
    @Test
    void validate_remaining_leave_is_bigger_than_confirming_vacations_and_throw_exception_case() {
        // CASE 잔여 연차일 15일, 현재 결재 진행 중인 휴가일 12(4, 4, 4)일, 신청한 휴가일 4일
        // 15 - (12 + 4) < 0 이므로 예외 발생

        //GIVEN
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 4, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 8, 0, 0);
        VacationDuration vacationDuration = new VacationDuration(startDate, endDate, LeaveDeduct.DEDUCT, VacationType.MORE_DAY);
        Organization organization = CoreEntityFactory.defalutOrganization();

        MemberLeave memberLeave = CoreEntityFactory.defaultMemberLeave(organization); // memberId == requesterId
        VacationManager vacationManager = VacationManager.createVacation(memberLeave, VacationType.MORE_DAY, LeaveDeduct.DEDUCT);
        Vacation vacation = vacationManager.getVacation();
        vacation.addVacationDuration(vacationDuration);

        Vacation vacation1 = Vacation.builder()
                .vacationStatus(VacationStatus.APPROVED)
                .requesterId("T0001")
                .build();

        vacation1.addVacationDuration(vacationDuration);

        Vacation vacation2 = Vacation.builder()
                .vacationStatus(VacationStatus.REQUEST)
                .requesterId("T0001")
                .build();
        vacation2.addVacationDuration(vacationDuration);
        Vacation vacation3 = Vacation.builder()
                .vacationStatus(VacationStatus.REQUEST)
                .requesterId("T0001")
                .build();
        vacation3.addVacationDuration(vacationDuration);

        List<Vacation> alreadyRequestVacations = List.of(vacation1, vacation2, vacation3);

        //WHEN - THEN
        assertThatThrownBy(() -> vacationManager.validateRemainingLeaveIsBiggerThanConfirmingVacationsAnd(alreadyRequestVacations))
                .isInstanceOf(VacationClientException.class);
    }

    @DisplayName("잔여 연차일 - (현재 결재 진행 중인 휴가 + 신청한 휴가일 수) 가 0을 포함한 양의 정수일 경우 " +
            "검증에 통과하고 예외가 발생하지 않는다. ")
    @ValueSource(ints = {1, 3})
    @ParameterizedTest()
    void validate_remaining_leave_is_bigger_than_confirming_vacations_and_not_throw_exception_case(int endDayOfMonth) {
        // CASE 잔여 연차일 15일, 현재 결재 진행 중인 휴가일 12(4, 4, 4)일, 신청한 휴가일 endDayOfMonth 일
        // 15 - (12 + 4) < 0 이므로 예외 발생

        //GIVEN
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, endDayOfMonth, 0, 0);
        List<VacationDuration> vacationDuration = List.of(new VacationDuration(startDate, endDate, LeaveDeduct.DEDUCT, VacationType.MORE_DAY));

        Organization organization = CoreEntityFactory.defalutOrganization();
        MemberLeave memberLeave = CoreEntityFactory.defaultMemberLeave(organization); // memberId == requesterId
        VacationManager vacationManager = VacationManager.createVacation(memberLeave, VacationType.MORE_DAY, LeaveDeduct.DEDUCT);

        Vacation vacation1 = Vacation.builder()
                .vacationStatus(VacationStatus.APPROVED)
                .requesterId("T0001")
                .build();

        Vacation vacation2 = Vacation.builder()
                .vacationStatus(VacationStatus.REQUEST)
                .requesterId("T0001")
                .build();

        Vacation vacation3 = Vacation.builder()
                .vacationStatus(VacationStatus.REQUEST)
                .requesterId("T0001")
                .build();

        List<Vacation> vacations = List.of(vacation1, vacation2, vacation3);

        //WHEN - THEN
        assertThatCode(() -> vacationManager.validateRemainingLeaveIsBiggerThanConfirmingVacationsAnd(vacations))
                .doesNotThrowAnyException();
    }

    // 인스턴스 생성 후, 필드들의 비즈니스적 올바름 검증

    // 상신 가능 여부 확인

    // 상신
    @DisplayName("VacationStatus.CREATE 일 때만 휴가 취소가 가능하며" +
            "취소할 시, vacationStatus 가 CANCEL 로 변경된다.")
    @Test
    void cancel_success_case() {
        Organization organization = CoreEntityFactory.defalutOrganization();
        MemberLeave memberLeave = CoreEntityFactory.defaultMemberLeave(organization);

        Vacation createdVacation = Vacation.builder()
                .vacationStatus(VacationStatus.CREATE)
                .build();

        VacationManager vacationManager = VacationManager.updateVacation(memberLeave, createdVacation);

        Vacation canceledVacation = vacationManager.cancel();
        assertThat(canceledVacation.getVacationStatus()).isEqualTo(VacationStatus.CANCELED);
    }

    // 취소 (결재 취소)
    @DisplayName("VacationStatus.CREATE 일 때만 휴가는 취소 가능하다. " +
            "그 외 상태일 때는 취소 불가능하고 VacationClientException 예외가 발생한다" +
            "CANCELED 상태일 경우 예외 메시지가 조금 다르게 출력되기에 아래 테스트 케이스에서 추가 작성")
    @EnumSource(names = {"REQUEST", "REJECT", "APPROVED", "NON_REQUIRED", "ONGOING", "COMPLETED", "FAIL", "ERROR"})
    @ParameterizedTest
    void cancel_fail_case(VacationStatus vacationStatus) {
        Organization organization = CoreEntityFactory.defalutOrganization();
        MemberLeave memberLeave = CoreEntityFactory.defaultMemberLeave(organization);

        Vacation createdVacation = Vacation.builder()
                .vacationStatus(vacationStatus)
                .build();

        VacationManager vacationManager = VacationManager.updateVacation(memberLeave, createdVacation);

        assertThatThrownBy(vacationManager::cancel)
                .isInstanceOf(VacationClientException.class)
                .hasMessageContaining("취소 가능한 상태의 휴가가 아닙니다. 현재 휴가 상태:");
    }
    @DisplayName("VacationStatus.CANCEL 일 때 휴가 취소를 요청할 경우 " +
            "VacationClientException 예외가 발생하며" +
            "취소 메시지는 상기 테스트와 조금 다르게 '이미 취소된 휴가입니다.' 라는 메시지가 나온다.")
    @Test
    void cancel_fail_case_cuz_already_canceled_vacation() {
        Organization organization = CoreEntityFactory.defalutOrganization();
        MemberLeave memberLeave = CoreEntityFactory.defaultMemberLeave(organization);

        Vacation createdVacation = Vacation.builder()
                .vacationStatus(VacationStatus.CANCELED)
                .build();

        VacationManager vacationManager = VacationManager.updateVacation(memberLeave, createdVacation);

        assertThatThrownBy(vacationManager::cancel)
                .isInstanceOf(VacationClientException.class)
                .hasMessage("이미 취소된 휴가입니다.");
    }

    // 수정
}