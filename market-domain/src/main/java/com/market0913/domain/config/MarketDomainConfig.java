package com.market0913.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@ComponentScan(basePackages = {"com.market0913.domain"})
@EntityScan("com.market0913.domain")
@EnableJpaRepositories("com.market0913.domain.repository")
public class MarketDomainConfig {
}
