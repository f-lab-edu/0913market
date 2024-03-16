package com.market0913.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;

public class MarketStatusJob {

    @Bean
    public Job updateStatusJob(JobRepository jobRepository, Step marketStatusStep) {
        return new JobBuilder("updateStatusJob", jobRepository)
                .start(marketStatusStep)
                .build();
    }
}
