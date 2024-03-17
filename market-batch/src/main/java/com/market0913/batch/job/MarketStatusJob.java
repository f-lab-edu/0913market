package com.market0913.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MarketStatusJob {

    @Bean
    public Job updateStatusJob(JobRepository jobRepository, Step marketStatusStep) {
        System.out.println("Job");
        return new JobBuilder("updateStatusJob", jobRepository)
                .start(marketStatusStep)
                .build();
    }
}
