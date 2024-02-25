package com.market0913.domain.model.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {

    FOOD("식품"),
    CLOTHING("의류"),
    COSMETIC("화장품"),
    HOUSEWARES("생활용품");

    private String value;
}
