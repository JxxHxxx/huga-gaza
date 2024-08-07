package com.jxx.groupware.api.member.dto.response;

import java.time.LocalDate;

public record MemberProjection(
    Long memberPk,
    LocalDate enteredDate,
    Integer experienceYears,
    Boolean memberActive,
    String memberId,
    String name,
    String companyId,
    String departmentId,
    Long organizationPk,
    String companyName,
    String departmentName,
    Boolean orgActive,
    String parentDepartmentId,
    String parentDepartmentName
) {
}
