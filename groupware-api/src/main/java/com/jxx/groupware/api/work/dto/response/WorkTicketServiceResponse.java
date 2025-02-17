package com.jxx.groupware.api.work.dto.response;

import com.jxx.groupware.core.work.domain.WorkRequester;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkTicketServiceResponse {
    private Long workTicketPk;
    private String workTicketId;
    private String workStatus;
    private LocalDateTime createdTime;
    private String chargeCompanyId;
    private String chargeDepartmentId;
    private String chargeDepartmentName;
    private LocalDateTime modifiedTime;
    private String requestTitle;
    private String requestContent;
    private WorkRequester workRequester;
}

