package com.market0913.api.controller.product;

import com.market0913.domain.model.product.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private int price;

    private String imageUrl;

    private String description;

    public static ProductResponse from(final Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .description(product.getDescription())
                .build();
    }
}
