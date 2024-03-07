package com.market0913.domain.repository;

import com.market0913.domain.model.market.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
