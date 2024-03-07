package com.market0913.api.controller.market;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.market.MarketDto;
import com.market0913.domain.model.market.MarketStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MarketResponse {

    private Long id;

    private String seller;

    private CategoryType category;

    private String name;

    private int price;

    private String imageUrl;

    private String description;

    private int discountPrice;

    private int quantity;

    private int salesQuantity;

    private int minSalesQuantity;

    private int limitQuantity;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private MarketStatus marketStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public static MarketResponse from(final MarketDto market) {
        return MarketResponse.builder()
                .id(market.getId())
                .seller(market.getSeller().getMemberId())
                .category(market.getCategory().getName())
                .name(market.getName())
                .price(market.getPrice())
                .imageUrl(market.getImageUrl())
                .description(market.getDescription())
                .discountPrice(market.getDiscountPrice())
                .quantity(market.getQuantity())
                .salesQuantity(market.getSalesQuantity())
                .minSalesQuantity(market.getMinSalesQuantity())
                .limitQuantity(market.getLimitQuantity())
                .salesStartDate(market.getSalesStartDate())
                .salesEndDate(market.getSalesEndDate())
                .marketStatus(market.getMarketStatus())
                .createdAt(market.getCreatedAt())
                .updatedAt(market.getUpdatedAt())
                .build();
    }
}
