package com.market0913.domain.model.order;

import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.market.MarketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderValidator {

    public void validate(Order order) {
        Market market = order.getMarket();
        if(market.getMarketStatus() != MarketStatus.WAIT) {
            throw new IllegalArgumentException(
                    String.format("주문은 구매 대기 상태인 마켓에서만 가능합니다 - %s", market.getMarketStatus())
            );
        }

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
