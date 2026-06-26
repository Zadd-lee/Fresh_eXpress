package com.mink.freshexpress.stock.schedule;

import com.mink.freshexpress.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockExpirationJob extends QuartzJobBean {

    private final StockService stockService;

    @Override
    protected void executeInternal(JobExecutionContext context) {

        log.info("===== 재고 상태 변경 Job Start =====");

        stockService.updateStatusToExpired();

        log.info("===== 재고 상태 변경 Job End =====");
    }
}