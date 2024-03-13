package com.market0913.api.controller.order;

import com.market0913.domain.model.order.OrderCreator;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@Getter
public class OrderRequest {

    @NotNull
    private Long marketId;

    @Range(min = 1)
    private int orderQuantity;

    @Range(min = 0)
    private int orderAmount;

    public OrderCreator toCreator(final String memberId) {
        return OrderCreator.builder()
                .memberId(memberId)
                .marketId(marketId)
                .orderQuantity(orderQuantity)
                .orderAmount(orderAmount)
                .build();
    }
}
