package com.market0913.batch.job.step;

import com.market0913.domain.model.market.Market;
import com.market0913.domain.service.market.MarketCreateService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MarketStatusStepConfig {

    public static final String STEP_NAME = "marketStatusStep";
    private final int chunkSize = 100;
    private final EntityManagerFactory entityManagerFactory;
    private final MarketCreateService marketService;

    @Bean
    public Step marketStatusStep(JobRepository jobRepository, JpaTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Market, Market>chunk(chunkSize, transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    public JpaPagingItemReader<Market> reader() {
        return new JpaPagingItemReaderBuilder<Market>()
                .name("marketStatusReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("select m from Market m where m.salesEndDate <= current_timestamp and m.marketStatus = 'WAIT' or m.marketStatus = 'SOLD_OUT' order by m.id")
                .build();
    }

    @Transactional
    public ItemWriter<Market> writer() {
        return items -> {
            marketService.updateMarketStatus((List<Market>) items.getItems());
        };
    }
}