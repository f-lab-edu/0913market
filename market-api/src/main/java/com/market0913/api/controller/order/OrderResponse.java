package com.market0913.api.controller.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.market0913.domain.model.order.OrderDto;
import com.market0913.domain.model.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {

    private Long id;

    private Long memberId;

    private Long marketId;

    private int orderQuantity;

    private int orderAmount;

    private OrderStatus orderStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static OrderResponse from(final OrderDto order) {
        return OrderResponse.builder()
                .id(order.getId())
                .memberId(order.getMemberId())
                .marketId(order.getMarket().getId())
                .orderQuantity(order.getOrderQuantity())
                .orderAmount(order.getOrderAmount())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
