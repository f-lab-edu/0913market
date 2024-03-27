package com.market0913.batch.job.step;

import com.market0913.domain.model.market.Market;
import com.market0913.domain.service.market.MarketCreateService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Configuration
public class MarketStatusStepConfig extends DefaultBatchConfiguration {

    public static final String STEP_NAME = "marketStatusStep";
    private final int chunkSize = 100;
    private final EntityManagerFactory entityManagerFactory;
    private final MarketCreateService marketService;

    @Bean
    @JobScope
    public Step marketStatusStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Market, Market>chunk(chunkSize, transactionManager)
                .reader(reader(null))
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Market> reader(@Value("#{jobParameters[requestDate]}") LocalDateTime requestDate) {
        LocalDateTime startDate = requestDate.toLocalDate().atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("startDate", startDate);
        parameterValues.put("endDate", endDate);

        return new JpaPagingItemReaderBuilder<Market>()
                .name("marketStatusReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("select m from Market m where m.salesEndDate between :startDate and :endDate and m.marketStatus in('WAIT', 'SOLD_OUT') order by m.id")
                .parameterValues(parameterValues)
                .build();
    }

    public ItemWriter<Market> writer() {
        return items -> {
            marketService.updateMarketStatus((List<Market>) items.getItems());
        };
    }
}