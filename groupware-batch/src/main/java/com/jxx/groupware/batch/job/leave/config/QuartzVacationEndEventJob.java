package com.jxx.groupware.batch.job.leave.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDate;
import java.util.UUID;

import static com.jxx.groupware.batch.GlobalJobConstant.VACATION_END_JOB_NAME;

@Slf4j
@DisallowConcurrentExecution // scheduler_Worker 스레드에서 동시 실행 방지
public class QuartzVacationEndEventJob extends QuartzJobBean {

    private final Job job;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    public QuartzVacationEndEventJob(@Qualifier(value = VACATION_END_JOB_NAME) Job job, JobLauncher jobLauncher, JobExplorer jobExplorer) {
        this.job = job;
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("execute by quartz-scheduler start {}", VACATION_END_JOB_NAME);
        LocalDate executeDateTime = LocalDate.now().minusDays(1l);

        JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
                .addString("run.id", UUID.randomUUID().toString(), true)
                .addString("executeSystem", "quartz")
                .addString("executeDateTime", executeDateTime.toString())
                .toJobParameters();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
