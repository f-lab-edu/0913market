package com.market0913.domain.model.market;

import com.market0913.domain.model.category.Category;
import com.market0913.domain.model.member.Member;
import com.market0913.domain.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketDto {

    private Long id;

    private Member seller;

    private Category category;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static MarketDto from(Market market) {
        Product product = market.getProduct();
        return MarketDto.builder()
                .id(market.getId())
                .seller(product.getSeller())
                .category(product.getCategory())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .description(product.getDescription())
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
