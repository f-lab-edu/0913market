package com.market0913.domain.model.order;

import com.market0913.domain.config.MarketDomainConfig;
import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.market.MarketStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MarketDomainConfig.class)
class OrderTest {

    @Autowired
    private OrderValidator orderValidator;

    @Test
    @DisplayName("주문 시 주문 상태를 구매 대기로 초기화한다")
    public void placeOrderTest() {
        // given
        Market market = Market.builder()
                .discountPrice(1000)
                .quantity(100)
                .salesQuantity(75)
                .minSalesQuantity(100)
                .limitQuantity(5)
                .salesStartDate(LocalDateTime.now().minusDays(1))
                .salesEndDate(LocalDateTime.now().plusDays(1))
                .marketStatus(MarketStatus.WAIT)
                .build();

        Order order = Order.builder()
                .memberId(market.getId())
                .market(market)
                .orderQuantity(2)
                .orderAmount(2000)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        order.placeOrder(orderValidator);

        // then
        assertEquals(OrderStatus.PU_WAIT, order.getOrderStatus());
    }
}