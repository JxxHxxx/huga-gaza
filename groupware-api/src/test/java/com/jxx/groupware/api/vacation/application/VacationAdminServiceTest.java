package com.jxx.groupware.api.vacation.application;


import com.jxx.groupware.api.member.application.UserSession;
import com.jxx.groupware.api.vacation.dto.request.CommonVacationForm;
import com.jxx.groupware.api.vacation.dto.request.CommonVacationServiceForm;
import com.jxx.groupware.api.vacation.dto.response.CommonVacationServiceResponse;
import com.jxx.groupware.api.vacation.dto.response.VacationServiceResponse;
import com.jxx.groupware.core.vacation.domain.entity.VacationStatus;
import com.jxx.groupware.core.vacation.domain.exeception.VacationClientException;
import com.jxx.groupware.core.vacation.infra.VacationDurationRepository;
import com.jxx.groupware.core.vacation.infra.VacationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class VacationAdminServiceTest {
    @Autowired
    VacationAdminService vacationAdminService;
    @Autowired
    VacationRepository vacationRepository;
    @Autowired
    VacationDurationRepository vacationDurationRepository;

    @DisplayName("공동 연차 등록 정상 케이스" +
            "공동 연차 글로벌 정책 " +
            "1. 하루 단위로만 등록 가능하다." +
            "2. 결재 여부, 개인 연차에서 차감 여부를 결정해야 한다." +
            "3. 결재 X, 차감 O 라면 선차감되어 API 정상 응답 시, 바로 사용자의 잔여 연차가 감소한다." +
            "4. 결재 O, 차감 O 의 경우 개인 연차 신청 API와 동일한 플로우를 탄다.")
    @Test
    void assign_common_vacation_success_case() {
        //given
        UserSession userSession = new UserSession("JXX", "제이주식회사", "U00001", "이재헌", "J00001", "IT센터");
        LocalDate vacationDate = LocalDate.of(2024, 4, 17);
        CommonVacationForm form = new CommonVacationForm(
                "JXX",
                false,
                false,
                List.of(vacationDate),
                "U00001", "이재헌", "J00001", "IT센터");
        CommonVacationServiceForm serviceForm = new CommonVacationServiceForm(userSession, form);
        //when
        CommonVacationServiceResponse response = vacationAdminService.assignCommonVacation(serviceForm);
        //then
        VacationServiceResponse vacationServiceResponse = response.vacations().get(0);
        assertThat(vacationServiceResponse.vacationDuration())
                .extracting("startDateTime").containsExactly(vacationDate.atStartOfDay());

        assertThat(vacationServiceResponse.vacationDuration())
                .extracting("endDateTime").containsExactly(vacationDate.atTime(23, 59, 59));

        assertThat(vacationServiceResponse.vacationStatus()).isEqualTo(VacationStatus.NON_REQUIRED);
    }

    @DisplayName("동일한 요일을 공동 연차로 중복 신청할 시, VacationClientException 발생한다.")
    @Test
    void assign_common_vacation_fail_cause_duplication_vacation_date() {
        //given
        UserSession userSession = new UserSession("JXX", "제이주식회사", "U00001", "이재헌", "J00001", "IT센터");
        LocalDate vacationDate = LocalDate.of(2024, 4, 17);
        CommonVacationForm form = new CommonVacationForm("JXX", false, false, List.of(vacationDate), "", "", "", "");
        CommonVacationServiceForm serviceForm = new CommonVacationServiceForm(userSession, form);
        //when
        CommonVacationServiceResponse firstResponse = vacationAdminService.assignCommonVacation(serviceForm);

        assertThatThrownBy(() -> vacationAdminService.assignCommonVacation(serviceForm))
                .isInstanceOf(VacationClientException.class)
                .hasMessageContaining("은 이미 공동 연차로 등록되어 있는 날짜입니다.");
        //when

        //then
    }

    @DisplayName("존재하지 않는 회사코드로 공동 연차 신청을 할 경우, VacationClientException 발생한다.")
    @Test
    void assign_common_vacation_fail_cause_not_exist_company_Id() {
        //given
        UserSession userSession = new UserSession("JXX", "제이주식회사", "U00001", "이재헌", "J00001", "IT센터");
        LocalDate vacationDate = LocalDate.of(2024, 4, 17);
        CommonVacationForm notExistCompanyIdContainForm = new CommonVacationForm("XXX",
                false,
                false,
                List.of(vacationDate),
                "U00001", "이재헌", "J00001", "IT센터");
        CommonVacationServiceForm serviceForm = new CommonVacationServiceForm(userSession, notExistCompanyIdContainForm);
        //when
        assertThatThrownBy(() -> vacationAdminService.assignCommonVacation(serviceForm))
                .isInstanceOf(VacationClientException.class)
                .hasMessageContaining("존재하지 않는 회사 코드");
        //when

        //then
    }
}