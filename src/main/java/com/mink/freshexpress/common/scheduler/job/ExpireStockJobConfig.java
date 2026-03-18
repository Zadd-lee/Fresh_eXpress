package com.mink.freshexpress.common.scheduler.job;
package com.example.batch.job;

import com.example.batch.tasklet.ExpireStockTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ExpireStockJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ExpireStockTasklet expireStockTasklet;

    @Bean
    public Step expireStockStep() {
        return new StepBuilder("expireStockStep", jobRepository)
                .tasklet(expireStockTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job expireStockJob() {
        return new JobBuilder("expireStockJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(expireStockStep())
                .build();
    }
}