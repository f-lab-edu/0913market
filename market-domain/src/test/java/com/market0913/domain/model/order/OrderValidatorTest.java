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
class OrderValidatorTest {

    @Autowired
    private OrderValidator orderValidator;

    @Test
    @DisplayName("주문은 구매 대기 상태인 마켓에서만 가능하다")
    public void orderValidatorTest_1() {
        // given
        Market market = Market.builder()
                .discountPrice(1000)
                .quantity(100)
                .salesQuantity(75)
                .minSalesQuantity(100)
                .limitQuantity(5)
                .salesStartDate(LocalDateTime.now().minusDays(1))
                .salesEndDate(LocalDateTime.now().plusDays(1))
                .marketStatus(MarketStatus.SOLD_OUT)
                .build();

        Order order = Order.builder()
                .market(market)
                .orderQuantity(2)
                .orderAmount(2000)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> order.placeOrder(orderValidator));
    }

    @Test
    @DisplayName("주문은 인당 최대 구매 가능 수량을 초과할 수 없다")
    public void orderValidatorTest_2() {
        // given
        int limitQuantity = 5;
        int orderQuantity = 10;

        Market market = Market.builder()
                .discountPrice(1000)
                .quantity(100)
                .salesQuantity(75)
                .minSalesQuantity(100)
                .limitQuantity(limitQuantity)
                .salesStartDate(LocalDateTime.now().minusDays(1))
                .salesEndDate(LocalDateTime.now().plusDays(1))
                .marketStatus(MarketStatus.SOLD_OUT)
                .build();

        Order order = Order.builder()
                .market(market)
                .orderQuantity(orderQuantity)
                .orderAmount(2000)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> order.placeOrder(orderValidator));
    }

    @Test
    @DisplayName("주문 수량은 재고 수량을 초과할 수 없다")
    public void orderValidatorTest_3() {
        // given
        int totalQuantity = 100;
        int salesQuantity = 97;
        int orderQuantity = 5;

        Market market = Market.builder()
                .discountPrice(1000)
                .quantity(totalQuantity)
                .salesQuantity(salesQuantity)
                .minSalesQuantity(100)
                .limitQuantity(5)
                .salesStartDate(LocalDateTime.now().minusDays(1))
                .salesEndDate(LocalDateTime.now().plusDays(1))
                .marketStatus(MarketStatus.WAIT)
                .build();

        Order order = Order.builder()
                .market(market)
                .orderQuantity(orderQuantity)
                .orderAmount(2000)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        // then
        assertThrows(IllegalStateException.class, () -> order.placeOrder(orderValidator));
    }

    @Test
    @DisplayName("주문은 마켓 진행 기간에만 가능하다")
    public void orderValidatorTest_4() {
        // given
        LocalDateTime orderDate = LocalDateTime.now();
        LocalDateTime salesStartDate = orderDate.minusDays(2);
        LocalDateTime salesEndDate = orderDate.minusDays(1);

        Market market = Market.builder()
                .discountPrice(1000)
                .quantity(100)
                .salesQuantity(75)
                .minSalesQuantity(100)
                .limitQuantity(5)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .marketStatus(MarketStatus.WAIT)
                .build();

        Order order = Order.builder()
                .market(market)
                .orderQuantity(2)
                .orderAmount(2000)
                .createdAt(orderDate)
                .build();

        // when
        // then
        assertThrows(IllegalStateException.class, () -> order.placeOrder(orderValidator));
    }

    @Test
    @DisplayName("주문 금액이 주문 수량, 마켓 가격과 일치하지 않으면 예외가 발생한다")
    public void orderValidatorTest_5() {
        // given
        int orderQuantity = 2;
        int marketPrice = 1000;
        int orderAmount = orderQuantity * marketPrice - 1;

        Market market = Market.builder()
                .discountPrice(marketPrice)
                .quantity(100)
                .salesQuantity(75)
                .minSalesQuantity(100)
                .limitQuantity(5)
                .salesStartDate(LocalDateTime.now().minusDays(1))
                .salesEndDate(LocalDateTime.now().plusDays(1))
                .marketStatus(MarketStatus.WAIT)
                .build();

        Order order = Order.builder()
                .market(market)
                .orderQuantity(orderQuantity)
                .orderAmount(orderAmount)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> order.placeOrder(orderValidator));
    }
}