package com.market0913.api.controller.product;

import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.product.ProductDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private Long id;

    private String seller;

    private CategoryType category;

    private String name;

    private int price;

    private String imageUrl;

    private String description;

    public static ProductResponse from(final ProductDto product) {
        return ProductResponse.builder()
                .id(product.getId())
                .seller(product.getSeller().getMemberId())
                .category(product.getCategory().getName())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .description(product.getDescription())
                .build();
    }
}