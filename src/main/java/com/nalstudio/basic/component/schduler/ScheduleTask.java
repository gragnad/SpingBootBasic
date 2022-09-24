package com.nalstudio.basic.component.schduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class ScheduleTask {
    @Scheduled(fixedDelay = 2000)
    public void testTask() {
        log.info("TestTask : " + LocalDateTime.now());
    }
}
