package com.jxx.vacation.api.vacation.application;

import com.jxx.vacation.api.member.application.UserSession;
import com.jxx.vacation.api.vacation.dto.request.CommonVacationForm;
import com.jxx.vacation.api.vacation.dto.request.CommonVacationServiceForm;
import com.jxx.vacation.api.vacation.dto.request.CompanyVacationTypePolicyForm;
import com.jxx.vacation.api.vacation.dto.request.CompanyVacationTypePolicyRequest;
import com.jxx.vacation.api.vacation.dto.response.VacationTypePolicyResponse;
import com.jxx.vacation.api.vacation.listener.CommonVacationCreateEvent;
import com.jxx.vacation.core.common.Creator;
import com.jxx.vacation.core.vacation.domain.entity.*;
import com.jxx.vacation.core.vacation.infra.CompanyVacationTypePolicyRepository;
import com.jxx.vacation.core.vacation.infra.MemberLeaveRepository;
import com.jxx.vacation.core.vacation.infra.VacationDurationRepository;
import com.jxx.vacation.core.vacation.infra.VacationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationAdminService {

    private final CompanyVacationTypePolicyRepository companyVacationTypePolicyRepository;
    private final MemberLeaveRepository memberLeaveRepository;
    private final VacationRepository vacationRepository;
    private final VacationDurationRepository vacationDurationRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final String COMMON_VACATION_DEPARTMENT_CODE = "ALL";

    @Transactional
    public List<VacationTypePolicyResponse> addCompanyVacationTypePolicies(CompanyVacationTypePolicyRequest policyRequest) {
        List<CompanyVacationTypePolicyForm> forms = policyRequest.form();
        List<CompanyVacationTypePolicy> policies = forms.stream()
                .map(form -> new CompanyVacationTypePolicy(
                        form.companyId(),
                        VacationType.valueOf(form.vacationType()),
                        form.vacationDay(),
                        new Creator(policyRequest.adminId(),"ADMIN-API")))
                .toList();

        List<CompanyVacationTypePolicy> savedPolicies = companyVacationTypePolicyRepository.saveAll(policies);
        return savedPolicies.stream()
                .map(policy -> new VacationTypePolicyResponse(policy.getCompanyId(), policy.getVacationType(), policy.getVacationDay()))
                .toList();

    }

    // TODO 몇 명이 반영됐고 몇 일 반영 됐는지
    @Transactional
    public int assignCommonVacation(CommonVacationServiceForm vacationServiceForm) {
        // 검증 전역 관리자 혹은 사내 관리자 검증
        UserSession userSession = vacationServiceForm.userSession();
        CommonVacationForm commonVacationForm = vacationServiceForm.commonVacationForm();
        List<LocalDate> vacationDates = commonVacationForm.vacationDates();

        final boolean mustApproval = commonVacationForm.mustApproval();
        final boolean deducted = commonVacationForm.deducted();
        LeaveDeduct leaveDeduct = decideLeaveDeduct(mustApproval, deducted);

        List<Vacation> vacations = new ArrayList<>();
        List<VacationDuration> vacationDurations = new ArrayList<>();
        for (LocalDate vacationDate : vacationDates) {
            Vacation commonVacation = Vacation.builder()
                    .vacationStatus(VacationStatus.CREATE)
                    .vacationType(VacationType.COMMON_VACATION)
                    .requesterId(userSession.getMemberId()) // check
                    .companyId(commonVacationForm.companyId())
                    .leaveDeduct(leaveDeduct)
                    .build();

            VacationDuration vacationDuration = new VacationDuration(
                    vacationDate.atStartOfDay(), vacationDate.atTime(23, 59, 59), leaveDeduct);
            vacationDuration.setLastDuration("Y");
            vacationDuration.mappingVacation(commonVacation);

            vacationDurations.add(vacationDuration);
            vacations.add(commonVacation);
        }
        List<Vacation> savedVacations = vacationRepository.saveAll(vacations);
        vacationDurationRepository.saveAll(vacationDurations);

        int updateRows = 0;
        // TODO 배치에서 이중 차감
        // 결재 X, 차감 O 일 경우 바로 차감
        if (!mustApproval && deducted) {
            int leaveDate = vacationDates.size();
            updateRows = memberLeaveRepository.updateRemainingLeave(leaveDate, commonVacationForm.companyId());
        }

        // 결재 승인을 받아야 하는 경우, messageQ 생성
        if (mustApproval) {
            List<Vacation> succeedVacations = savedVacations.stream()
                    .filter(vacation -> vacation.successRequest())
                    .toList();

            final float vacationDate = 1f; // 공동 연차는 신청 1개에 하루라는 정책이 존재하기 때문에 해당 값 써도 안전
            for (Vacation succeedVacation : succeedVacations) {
                CommonVacationCreateEvent commonVacationCreateEvent = new CommonVacationCreateEvent(
                        succeedVacation.getRequesterId(),
                        succeedVacation.getCompanyId(),
                        COMMON_VACATION_DEPARTMENT_CODE,
                        vacationDate,
                        succeedVacation.getId()
                );
                eventPublisher.publishEvent(commonVacationCreateEvent);
            }
        }

        return updateRows;
    }

    private LeaveDeduct decideLeaveDeduct(boolean mustApproval, boolean deducted) {
        if (!deducted) {
            return LeaveDeduct.NOT_DEDUCT;
        }

        boolean preDeduct = deducted && !mustApproval;
        return preDeduct ? LeaveDeduct.PRE_DEDUCT : LeaveDeduct.DEDUCT;
    }
}
