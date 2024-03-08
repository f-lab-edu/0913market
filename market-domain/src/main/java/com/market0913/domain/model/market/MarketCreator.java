package com.market0913.domain.model.market;

import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.product.Product;
import com.market0913.domain.model.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MarketCreator {

    private String sellerId;

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

    public static Market createMarket(MarketCreator creator, ProductDto productDto) {
        return Market.builder()
                .product(
                    Product.builder()
                        .seller(productDto.getSeller())
                        .category(productDto.getCategory())
                        .name(productDto.getName())
                        .price(productDto.getPrice())
                        .imageUrl(productDto.getImageUrl())
                        .description(productDto.getDescription())
                    .build()
                )
                .discountPrice(creator.getDiscountPrice())
                .quantity(creator.getQuantity())
                .salesQuantity(creator.getSalesQuantity())
                .minSalesQuantity(creator.getMinSalesQuantity())
                .limitQuantity(creator.getLimitQuantity())
                .salesStartDate(creator.getSalesStartDate())
                .salesEndDate(creator.getSalesEndDate())
                .marketStatus(MarketStatus.WAIT)
                .build();
    }
}
