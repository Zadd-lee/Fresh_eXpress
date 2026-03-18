package com.mink.freshexpress.common.scheduler.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LaunchExpireStockBatchJob extends QuartzJobBean {

    private final JobLauncher jobLauncher;
    private final Job expireStockJob;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis()) // 매 실행마다 유니크
                    .toJobParameters();

            jobLauncher.run(expireStockJob, jobParameters);

        } catch (Exception e) {
            log.error("배치 실행 실패", e);
            throw new JobExecutionException(e);
        }
    }
}