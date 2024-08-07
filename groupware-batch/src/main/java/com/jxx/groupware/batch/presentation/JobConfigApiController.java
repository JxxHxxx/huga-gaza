package com.jxx.groupware.batch.presentation;

import com.jxx.groupware.batch.application.JobConfigService;
import com.jxx.groupware.batch.dto.request.EnrollJobForm;
import com.jxx.groupware.batch.dto.request.JobHistoryCond;
import com.jxx.groupware.batch.dto.request.ScheduleJobUpdateRequest;
import com.jxx.groupware.batch.dto.request.TriggerCreateRequest;
import com.jxx.groupware.batch.dto.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobConfigApiController {

    private final JobConfigService jobConfigService;

    @PostMapping("/admin/batch/jobs")
    public ResponseEntity<?> enrollJobInformation(@RequestBody EnrollJobForm enrollJobForm) {
        EnrollJobResponse responses = jobConfigService.enrollBatchJob(enrollJobForm);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/admin/batch/jobs")
    public ResponseEntity<?> getJobInformation() {
        List<JobMetadataResponse> responses = jobConfigService.findAllJob();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/admin/batch/jobs/{job-name}/parameters")
    public ResponseEntity<?> getJobParameters(@PathVariable("job-name") String jobName) {
        List<JobParamResponse> responses = jobConfigService.findJobParameterBy(jobName);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/admin/batch/jobs-hist")
    public ResponseEntity<?> getJobs(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                     @ModelAttribute @Valid JobHistoryCond cond) {
        Page<JobHistoryResponse> responses = jobConfigService.pageJobHistories(cond, page, size);
        return ResponseEntity.ok(responses);
    }


    /**
     * 트리거 등록 API
     * 트리거는 등록되자마자 설정된 cronExpression 에 맞춰 동작한다.
     * @param request
     * @return
     */
    @PostMapping("/admin/batch/triggers")
    public ResponseEntity<?> createTrigger(@RequestBody TriggerCreateRequest request) {
        TriggerCreateResponse response = jobConfigService.createTrigger(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 트리거 수정 API
     * @param request
     * @return
     */
    @PatchMapping("/admin/batch/triggers")
    public ResponseEntity<?> rescheduleTrigger(@RequestBody ScheduleJobUpdateRequest request) {
        jobConfigService.rescheduleTrigger(request);
        return ResponseEntity.ok("갱신 완료");
    }

    /**
     *
     * @param jobName : scheduler 에 등록된 jobName 임 즉 jobDetails 의 Name
     * @return
     */
    @PatchMapping("/admin/batch/triggers/pause")
    public ResponseEntity<?> pauseJobFromScheduler(@RequestParam("jobName") String jobName) {
        jobConfigService.pauseJobFromScheduler(jobName);
        return ResponseEntity.ok(jobName + "스케줄러 중지 완료");
    }

    @GetMapping("/admin/batch/triggers")
    public ResponseEntity<?> readTriggerInformation(@RequestParam("triggerName") String triggerName) {
        JobSchedulingResponse jobSchedulingResponse = jobConfigService.readTriggerInformation(triggerName);
        return ResponseEntity.ok(jobSchedulingResponse);
    }

    @GetMapping("/admin/batch/triggers/all")
    public ResponseEntity<?> readAllTriggerInformation() {
        List<JobSchedulingResponse> jobSchedulingResponse = jobConfigService.readAllTriggerInformation();
        return ResponseEntity.ok(jobSchedulingResponse);
    }
}
