package com.market0913.domain.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
    SELLER("판매자"),
    BUYER("구매자");

    private String value;
}
