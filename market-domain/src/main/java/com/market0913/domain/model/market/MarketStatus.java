package com.market0913.domain.model.market;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MarketStatus {

    WAIT("구매 대기"),
    SOLD_OUT("품절"),
    AVAILABLE("구매 가능"),
    NOT_AVAILABLE("구매 불가");

    private String value;
}
