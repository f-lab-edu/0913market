package com.market0913.domain.repository;

import com.market0913.domain.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByMarketIdAndMemberId(Long marketId, Long memberId);
}
