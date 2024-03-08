package com.market0913.domain.repository;

import com.market0913.domain.model.market.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {

    Optional<Market> findById(Long id);

    @Query("select m from Market m join fetch m.product join fetch m.product.seller join fetch m.product.category where m.id=:id")
    Optional<Market> findByIdWithProduct(Long id);
}
