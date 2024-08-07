package com.jxx.groupware.batch.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "JXX_BATCH_JOB_META_DATA")
public class JobMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_META_DATA_PK")
    private int pk;
    @Column(name = "JOB_NAME", unique = true)
    @Comment(value = "잡 이름(고유한 값으로 사실상 잡ID)")
    private String jobName;
    @Column(name = "JOB_DESCRIPTION")
    @Comment(value = "잡 설명")
    private String jobDescription;

    @Column(name = "ENROLLED_TIME")
    @Comment(value = "최초 잡 등록 시간")
    private LocalDateTime enrolledTime;

    @Comment(value = "논리 FK, 잡 메타테이블 PK")
    @OneToMany(mappedBy = "jobMetaData")
    private List<JobParam> jobParams = new ArrayList<>();

    @Builder
    public JobMetaData(String jobName,
                       String jobDescription,
                       LocalDateTime enrolledTime) {
        this.jobName = jobName;
        this.jobDescription = jobDescription;
        this.enrolledTime = enrolledTime;
    }
}
