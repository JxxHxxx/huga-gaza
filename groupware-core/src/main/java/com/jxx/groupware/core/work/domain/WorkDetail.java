package com.jxx.groupware.core.work.domain;

import com.jxx.groupware.core.work.domain.exception.WorkClientException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "JXX_WORK_DETAIL_MASTER")
public class WorkDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORK_DETAIL_PK")
    @Comment("작업 내역 PK")
    private Long workDetailPk;
    @Column(name = "ANALYZE_CONTENT")
    @Comment("작업 분석 내용")
    private String analyzeContent;
    @Column(name = "ANALYZE_COMPLETED_TIME")
    @Comment("작업 분석 완료 시간")
    private LocalDateTime analyzeCompletedTime;
    @Column(name = "WORK_PLAN_CONTENT")
    @Comment("작업 계획 내용")
    private String workPlanContent;
    @Column(name = "WORK_PLAN_COMPLETED_TIME")
    @Comment("작업 계획 완료 시간")
    private LocalDateTime workPlanCompletedTime;
    @Column(name = "EXPECT_DEADLINE_DATE")
    @Comment("작업 마감 예상일")
    private LocalDate expectDeadlineDate;
    @Column(name = "RECEIVER_ID")
    @Comment("접수자 ID")
    private String receiverId;
    @Column(name = "RECEIVER_NAME")
    @Comment("접수자 이름")
    private String receiverName;
    @Column(name = "CREATE_TIME")
    @Comment("생성 시간")
    private LocalDateTime createTime;
    @Column(name = "PRE_REFLECT")
    @Comment("작업 선처리 여부")
    private Boolean preReflect;
    @Column(name = "PRE_REFLECT_REASON")
    @Comment("작업 선처리 사유")
    private String preReflectReason;
    @Column(name = "REJECT_REASON")
    @Comment("작업 반려 이유")
    private String rejectReason;

    @Builder
    public WorkDetail(String analyzeContent, LocalDateTime analyzeCompletedTime, String workPlanContent,
                      LocalDateTime workPlanCompletedTime, LocalDate expectDeadlineDate, String receiverId,
                      String receiverName, LocalDateTime createTime, Boolean preReflect, String preReflectReason,
                      String rejectReason) {
        this.analyzeContent = analyzeContent;
        this.analyzeCompletedTime = analyzeCompletedTime;
        this.workPlanContent = workPlanContent;
        this.workPlanCompletedTime = workPlanCompletedTime;
        this.expectDeadlineDate = expectDeadlineDate;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.createTime = createTime;
        this.preReflect = preReflect;
        this.preReflectReason = preReflectReason;
        this.rejectReason = rejectReason;
    }

    public void completeAnalyzeContent(String analyzeContent) {
        if (analyzeContent == null) {
            throw new WorkClientException("분석 내용은 null 일 수 없습니다.");
        }
        if (analyzeContent.isBlank()) {
            throw new WorkClientException("분석 내용은 공백일 수 없습니다");
        }
        this.analyzeContent = analyzeContent;
        this.analyzeCompletedTime = LocalDateTime.now();
    }

    public void completeWorkPlan(String workPlanContent) {
        if (workPlanContent == null) {
            throw new WorkClientException("계획 내용은 null 일 수 없습니다.");
        }
        if (workPlanContent.isBlank()) {
            throw new WorkClientException("계획 내용은 공백일 수 없습니다");
        }

        this.workPlanContent = workPlanContent;
        this.workPlanCompletedTime = LocalDateTime.now();
    }

    /** WRITE QUERY
     *
     */
    protected void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
