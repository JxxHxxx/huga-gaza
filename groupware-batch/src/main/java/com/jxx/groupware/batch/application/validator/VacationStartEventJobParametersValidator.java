package com.jxx.groupware.batch.application.validator;

import com.jxx.groupware.batch.exception.JxxJobExecutionException;
import com.jxx.groupware.batch.job.parameters.JxxJobParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service("vacation.start.job.parameter-validator")
public class VacationStartEventJobParametersValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) {
        log.info("start vacation.status-manage.job parameter validate");
        List<String> requiredParameterKeyNames = JxxJobParameter.VACATION_STATUS_MANAGE_REQUIRED_GROUP;

        List<String> notPreparedRequiredParams = requiredParameterKeyNames.stream()
                .filter(keyName -> Objects.isNull(parameters.getParameter(keyName)))
                .toList();

        if (!notPreparedRequiredParams.isEmpty()) {
            String message = String.format("필수 잡 파라미터 %s 가 존재하지 않습니다.", notPreparedRequiredParams);
            log.error("vacation.status-manage.job required parameter's {} not prepared", notPreparedRequiredParams);
            throw new JxxJobExecutionException(message);
        }

    }
}
