package com.market0913.domain.model.order;

import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.market.MarketStatus;
import com.market0913.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrderValidator {

    private final OrderRepository orderRepository;

    public void validate(Order order) {
        Market market = order.getMarket();
        if(market.getMarketStatus() != MarketStatus.WAIT) {
            throw new IllegalArgumentException("주문은 구매 대기 상태인 마켓에서만 가능합니다 - %s".formatted(market.getMarketStatus()));
        }

        Long marketId = market.getId();
        Long memberId = order.getMemberId();
        Optional<Order> previousOrder = orderRepository.findByMarketIdAndMemberId(marketId, memberId);
        previousOrder.ifPresent(o -> {
            // TODO : 주문 수량 변경 API 추가
            throw new IllegalArgumentException("해당 마켓에서의 주문 이력이 존재합니다. - 마켓 번호 : %d, 주문 번호 : %d".formatted(marketId, o.getId()));
        });

        if(market.getLimitQuantity() < order.getOrderQuantity()) {
            throw new IllegalArgumentException("인당 최대 구매 가능 수량을 초과했습니다.");
        }

        int stockQuantity = market.getQuantity() - market.getSalesQuantity();
        if(stockQuantity < order.getOrderQuantity()) {
            throw new IllegalStateException("재고 수량이 부족합니다.");
        }

        if(order.getCreatedAt().isBefore(market.getSalesStartDate()) ||
            order.getCreatedAt().isAfter(market.getSalesEndDate())) {
            throw new IllegalStateException("마켓 진행 기간이 아닙니다.");
        }

        int orderAmount = market.getDiscountPrice() * order.getOrderQuantity();
        if(orderAmount != order.getOrderAmount()) {
            throw new IllegalArgumentException("주문 금액이 일치하지 않습니다.");
        }
    }
}
