package com.market0913.batch.step;

import com.market0913.domain.model.market.Market;
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

@RequiredArgsConstructor
@Component
public class MarketStatusStep {

    private final int chunkSize = 10;
    private final EntityManagerFactory entityManagerFactory;

    @Bean("marketStatus")
    public Step marketStatusStep(JobRepository jobRepository) {
        return new StepBuilder("marketStatusStep", jobRepository)
                .<Market, Market>chunk(chunkSize, new JpaTransactionManager())
                .reader(reader())
                .writer(writer())
                .build();
    }

    public JpaPagingItemReader<Market> reader() {
        JpaPagingItemReader<Market> jpaPagingItemReader= new JpaPagingItemReaderBuilder<Market>()
                .name("marketStatusReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                //.queryString("select m from Market m where m.salesEndDate <= current_timestamp and m.marketStatus='WAIT' or m.marketStatus='SOLD_OUT' order by m.id")
                .queryString("select m from Market m")
                .build();
        System.out.println("item count : " + jpaPagingItemReader.getCurrentItemCount());
        return jpaPagingItemReader;
    }

    public ItemWriter<Market> writer() {
        return items -> {
            for(Market market : items) {
                System.out.println(market.getMarketStatus());
            }
        };
    }
}