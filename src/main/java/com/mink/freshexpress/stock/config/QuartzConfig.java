package com.mink.freshexpress.stock.config;

import com.mink.freshexpress.stock.schedule.StockExpirationJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail stockExpirationJobDetail() {
        return JobBuilder.newJob(StockExpirationJob.class)
                .withIdentity("stockExpirationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger stockExpirationTrigger(JobDetail stockExpirationJobDetail) {

        return TriggerBuilder.newTrigger()
                .forJob(stockExpirationJobDetail)
                .withIdentity("stockExpirationTrigger")
                .withSchedule(
//                        CronScheduleBuilder.cronSchedule("0 0 0 * * ?")
                        CronScheduleBuilder.cronSchedule("* * * * * ?")
                )
                .build();
    }
}