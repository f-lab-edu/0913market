package com.market0913.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateStatusJobConfig extends DefaultBatchConfiguration {

    public static final String JOB_NAME = "updateStatusJob";

    @Bean
    public Job updateStatusJob(JobRepository jobRepository, Step marketStatusStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(marketStatusStep)
                .build();
    }
}