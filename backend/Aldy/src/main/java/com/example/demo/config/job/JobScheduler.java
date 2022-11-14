package com.example.demo.config.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final JobLauncher jobLauncher;

    private final CheckLevelJobConfig jobConfig;

    private final CountAlertJobConfig countAlertJobConfig;

    @Scheduled(cron = "0 0 0 * * *")
    public void countAlertJob() {

        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(countAlertJobConfig.countAlertJob(), jobParameters);
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void runCheckLevelJob() {

        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(jobConfig.checkLevelJob(), jobParameters);
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }


}
