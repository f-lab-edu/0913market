package com.market0913.api;

import com.market0913.common.MarketCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(MarketCommonConfig.class)
@SpringBootApplication
public class MarketApiApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application-api,application-common");
        SpringApplication.run(MarketApiApplication.class, args);
    }
}
