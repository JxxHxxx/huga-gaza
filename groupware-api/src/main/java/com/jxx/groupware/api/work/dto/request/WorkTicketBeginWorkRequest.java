package com.jxx.groupware.api.work.dto.request;

import com.jxx.groupware.core.work.dto.TicketReceiver;

public record WorkTicketBeginWorkRequest(
        TicketReceiver ticketReceiver
) {
}