package com.mink.freshexpress.common.scheduler.config;

import com.mink.freshexpress.common.scheduler.job.LaunchExpireStockBatchJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QuartzConfig {
    @Bean
    public JobDetail expireStockJobDetail() {
        return JobBuilder.newJob(LaunchExpireStockBatchJob.class)
                .withIdentity("expireStockQuartzJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger expireStockTrigger(JobDetail expireStockJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(expireStockJobDetail)
                .withIdentity("expireStockTrigger")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0 0 0 * * ?")
                )
                .build();
    }
}
