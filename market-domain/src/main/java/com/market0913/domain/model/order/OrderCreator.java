package com.market0913.domain.model.order;

import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OrderCreator {

    private String memberId;

    private Long marketId;

    private int orderQuantity;

    private int orderAmount;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    public static Order createOrder(Member member, Market market, OrderCreator creator) {
        return Order.builder()
                .memberId(member.getId())
                .market(market)
                .orderQuantity(creator.getOrderQuantity())
                .orderAmount(creator.getOrderAmount())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
