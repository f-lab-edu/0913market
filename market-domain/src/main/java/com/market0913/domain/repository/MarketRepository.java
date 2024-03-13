package com.market0913.domain.repository;

import com.market0913.domain.model.market.Market;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Market m where m.id=:marketId")
    Optional<Market> findByIdWithLock(Long marketId);

    @Query("select m from Market m join fetch m.product join fetch m.product.seller join fetch m.product.category where m.id=:marketId")
    Optional<Market> findByMarketIdWithProduct(Long marketId);

    @Query("select m from Market m join fetch m.product join fetch m.product.seller join fetch m.product.category where m.product.seller.memberId=:sellerId")
    List<Market> findBySellerIdWithProduct(String sellerId);
}
