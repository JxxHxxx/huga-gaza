package com.jxx.groupware.core.work.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "JXX_WORK_TICKET_HIST", indexes = {
        @Index(name = "IDX_HIST_WORK_TICKET_PK", columnList = "WORK_TICKET_PK"),
        @Index(name = "IDX_HIST_WORK_TICKET_ID", columnList = "WORK_TICKET_ID")})
public class WorkTicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORK_TICKET_HIST_PK")
    @Comment("작업 티켓 히스토리 PK")
    private Long workTicketHistoryPk;
    @Column(name = "WORK_TICKET_PK")
    @Comment("작업 티켓 PK")
    private Long workTicketPk;
    @Column(name = "WORK_TICKET_ID")
    @Comment("작업 티켓 ID")
    private String workTicketId;
    @Column(name = "REQUEST_TITLE")
    @Comment("요청 제목")
    private String requestTitle;
    @Column(name = "REQUEST_CONTENT")
    @Comment("요청 내용")
    private String requestContent;
    @Embedded
    private WorkRequester workRequester;
    @Column(name = "CHARGE_COMPANY_ID")
    @Comment("담당 회사(작업을 수행할) ID")
    private String chargeCompanyId;
    @Column(name = "CHARGE_DEPARTMENT_ID")
    @Comment("담당 부서(작업을 수행할) ID")
    private String chargeDepartmentId;
    @Column(name = "CREATED_TIME")
    @Comment("생성 시간")
    private LocalDateTime createdTime;
    @Column(name = "WORK_STATUS")
    @Comment("작업 진행 상태")
    @Enumerated(value = EnumType.STRING)
    private WorkStatus workStatus;
    @Column(name = "MODIFIED_TIME")
    @Comment("변경 시간")
    private LocalDateTime modifiedTime;
    // TODO 히스토리 레코드가 생성된 시간을 기록해야 한다.

    @Builder
    public WorkTicketHistory(Long workTicketPk, String workTicketId, String requestTitle, String requestContent, WorkRequester workRequester,
                             String chargeCompanyId, String chargeDepartmentId, LocalDateTime createdTime, WorkStatus workStatus,
                             LocalDateTime modifiedTime) {
        this.workTicketPk = workTicketPk;
        this.workTicketId = workTicketId;
        this.requestTitle = requestTitle;
        this.requestContent = requestContent;
        this.workRequester = workRequester;
        this.chargeCompanyId = chargeCompanyId;
        this.chargeDepartmentId = chargeDepartmentId;
        this.createdTime = createdTime;
        this.workStatus = workStatus;
        this.modifiedTime = modifiedTime;
    }

    public WorkTicketHistory(WorkTicket workTicket) {
        this.workTicketPk = workTicket.getWorkTicketPk();
        this.workTicketId = workTicket.getWorkTicketId();
        this.requestTitle = workTicket.getRequestTitle();
        this.requestContent = workTicket.getRequestContent();
        this.workRequester = workTicket.getWorkRequester();
        this.chargeCompanyId = workTicket.getChargeCompanyId();
        this.chargeDepartmentId = workTicket.getChargeDepartmentId();
        this.createdTime = workTicket.getCreatedTime();
        this.workStatus = workTicket.getWorkStatus();
        this.modifiedTime = workTicket.getModifiedTime();
    }
}
