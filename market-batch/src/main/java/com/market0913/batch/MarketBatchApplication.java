package com.market0913.batch;

import com.market0913.domain.config.MarketDomainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MarketDomainConfig.class)
@SpringBootApplication
public class MarketBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketBatchApplication.class, args);
    }

}
