package com.example.batchprocessing.infrastructure.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.example.batchprocessing.Application.LawnMowerProcessor;
import com.example.batchprocessing.Application.LawnMowerReader;
import com.example.batchprocessing.Application.LawnMowerWriter;
import com.example.batchprocessing.Application.RenameFileTasklet;
import com.example.batchprocessing.domain.model.LawnMower;
import com.example.batchprocessing.domain.model.Position;
import com.example.batchprocessing.infrastructure.adapter.output.FileLawnMowerRepository;
import com.example.batchprocessing.infrastructure.listner.JobCompletionNotificationListener;
import com.example.batchprocessing.port.LawnMowerRepository;
import com.example.batchprocessing.service.CommandProcessor;


@Configuration
public class BatchConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);
    private final JobRepository jobRepository;
    private final DataSourceTransactionManager transactionManager;

    public BatchConfiguration(JobRepository jobRepository, DataSourceTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public ItemReader<LawnMower> lawnMowerReader(LawnMowerRepository lawnMowerRepository) {
        return new LawnMowerReader(lawnMowerRepository);
    }

    @Bean
    public ItemProcessor<LawnMower, Position> lawnMowerProcessor(CommandProcessor commandProcessor) {
        return new LawnMowerProcessor(commandProcessor);
    }

    @Bean
    public ItemWriter<Position> lawnMowerWriter() {
        return new LawnMowerWriter();
    }

    @Bean
    public Step renameFileStep(RenameFileTasklet renameFileTasklet) {
        return new StepBuilder("renameFileStep", jobRepository)
                .tasklet(renameFileTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job lawnMowerJob(Step lawnMowerStep, Step renameFileStep, JobCompletionNotificationListener listener) {
        return new JobBuilder("lawnMowerJob", jobRepository)
            .listener(listener)
            .start(lawnMowerStep)
            .next(renameFileStep)
            .build();
    }

    @Bean
    public Step lawnMowerStep(ItemReader<LawnMower> lawnMowerReader,
                              ItemProcessor<LawnMower, Position> lawnMowerProcessor,
                              ItemWriter<Position> lawnMowerWriter) {
        return new StepBuilder("lawnMowerStep", jobRepository)
            .<LawnMower, Position>chunk(1, transactionManager)
            .reader(lawnMowerReader)
            .processor(lawnMowerProcessor)
            .writer(lawnMowerWriter)
            .listener(new StepExecutionListener() {
                @Override
                public void beforeStep(StepExecution stepExecution) {
                    logger.info("Starting lawnMowerStep");
                }

                @Override
                public ExitStatus afterStep(StepExecution stepExecution) {
                    logger.info("Completed lawnMowerStep with status {}", stepExecution.getStatus());
                    return stepExecution.getExitStatus();
                }
            })
            .build();
    }
}
