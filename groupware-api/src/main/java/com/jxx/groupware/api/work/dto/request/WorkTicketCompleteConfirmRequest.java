package com.jxx.groupware.api.work.dto.request;

public record WorkTicketCompleteConfirmRequest(
        String receiverCompanyId,
        String receiverDepartmentId,
        String receiverId,
        String workStatus
) {
}