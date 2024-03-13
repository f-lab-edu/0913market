package com.market0913.domain.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PU_WAIT("구매 대기"),
    PU_CANCEL("구매 취소"),
    NOT_AVAILABLE("구매 불가"),
    PAY_WAIT("결제 대기"),
    PAY_CANCEL("결제 취소"),
    PAY_COMPLETE("결제 완료");

    private String value;
}
