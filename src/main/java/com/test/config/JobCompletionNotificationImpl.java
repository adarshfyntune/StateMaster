package com.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationImpl implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationImpl.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job is started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Job is completed");
        }
    }
}
