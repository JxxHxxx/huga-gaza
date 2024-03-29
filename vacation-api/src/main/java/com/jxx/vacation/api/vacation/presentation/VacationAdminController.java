package com.jxx.vacation.api.vacation.presentation;

import com.jxx.vacation.api.vacation.application.VacationAdminService;
import com.jxx.vacation.api.vacation.dto.request.FamilyOccasionPolicyRequest;
import com.jxx.vacation.api.vacation.dto.response.FamilyOccasionPolicyResponse;
import com.jxx.vacation.api.vacation.dto.response.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 공동 연차는 설정 시, 차감되는 걸로 정책 수립
// 관리자 로직은 추후 처리 예정

@Slf4j
@RestController
@RequiredArgsConstructor
public class VacationAdminController {

    private final VacationAdminService vacationAdminService;

    // 공동 연차 지정 API

    // 공동 연차 수정 API

    // 공동 연차 삭제 API

    // 연차 충전 2024 -> 2025년으로 갔을 때 -> 이건 배치로 가야될듯.

    //
    @PostMapping("/admin/vacations/family-occasion-policies")
    public ResponseEntity<?> addFamilyOccasionPolicies(@RequestBody FamilyOccasionPolicyRequest request) {
        // 관리자 처리 로직
        List<FamilyOccasionPolicyResponse> responses = vacationAdminService.addFamilyOccasionPolicies(request.form());

        return ResponseEntity.ok(new ResponseResult<>(200, "경조 정책 생성 완료", responses));
    }
}
