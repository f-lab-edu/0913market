package com.market0913.api.controller.market;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.market.MarketCreator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class MarketRequest {

    @NotEmpty
    private String name;

    @Range(min = 1)
    private int price;

    @NotNull
    private CategoryType category;

    private String imageUrl;

    private String description;

    @Range(min = 0)
    private int discountPrice;

    @Range(min = 1)
    private int quantity;

    @Range(min = 0)
    private int salesQuantity;

    @Range(min = 1)
    private int minSalesQuantity;

    @Range(min = 1)
    private int limitQuantity;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime salesStartDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime salesEndDate;

    public MarketCreator toCreator(final String sellerId) {
        return MarketCreator.builder()
                .sellerId(sellerId)
                .category(category)
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .description(description)
                .discountPrice(discountPrice)
                .quantity(quantity)
                .salesQuantity(salesQuantity)
                .minSalesQuantity(minSalesQuantity)
                .limitQuantity(limitQuantity)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .category(category)
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .description(description)
                .build();
    }
}
