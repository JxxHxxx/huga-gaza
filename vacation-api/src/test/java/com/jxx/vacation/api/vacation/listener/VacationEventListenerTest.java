package com.jxx.vacation.api.vacation.listener;

import com.jxx.vacation.core.message.infra.MessageQRepository;
import com.jxx.vacation.core.vacation.domain.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class VacationEventListenerTest {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    MessageQRepository messageQRepository;

    @DisplayName("휴가 생성 이벤트 정상 호출 테스트")
    @Test
    void vacation_create_event_success() {
        //given
        MemberLeave memberLeave = MemberLeave.builder()
                .pk(100l)
                .memberId("U00100")
                .isActive(true)
                .experienceYears(2)
                .name("테스터")
                .leave(new Leave(15f, 15f))
                .enteredDate(LocalDate.of(2023, 5, 12))
                .organization(new Organization("JXX","JX사","J001", "IT팀"))
                .build();
        Vacation vacation = Vacation.builder()
                .requesterId("U00100")
                .vacationDuration(new VacationDuration(VacationType.MORE_DAY,
                        LocalDateTime.of(2023, 5, 15, 0, 0),
                        LocalDateTime.of(2023, 5, 17, 0, 0)))
                .vacationStatus(VacationStatus.CREATE)
                .deducted(true)
                .build();

        //when - then
        assertThatCode(() -> eventPublisher.publishEvent(new VacationCreatedEvent(memberLeave, vacation, 2l, "U00100")))
                .doesNotThrowAnyException();
    }

}