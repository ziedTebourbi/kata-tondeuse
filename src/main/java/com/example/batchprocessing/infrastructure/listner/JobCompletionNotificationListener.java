package com.example.batchprocessing.infrastructure.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job {} is about to start...", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Job {} completed successfully!", jobExecution.getJobInstance().getJobName());
            
            
            
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            logger.error("Job {} failed with errors.", jobExecution.getJobInstance().getJobName());

          
            
            jobExecution.getAllFailureExceptions().forEach(exception -> 
                logger.error("Exception occurred: ", exception)
            );
        }
    }
}
