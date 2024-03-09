package com.market0913.domain.model.order;

import com.market0913.domain.model.market.Market;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private Long memberId;

    private Market market;

    private int orderQuantity;

    private int orderAmount;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime canceledAt;

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .memberId(order.getMemberId())
                .market(order.getMarket())
                .orderQuantity(order.getOrderQuantity())
                .orderAmount(order.getOrderAmount())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .canceledAt(order.getCanceledAt())
                .build();
    }
}
